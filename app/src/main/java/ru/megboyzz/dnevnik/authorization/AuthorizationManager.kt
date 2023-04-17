package ru.megboyzz.dnevnik.authorization

import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Connection
import org.jsoup.Jsoup
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.megboyzz.dnevnik.db.AppDataBase
import ru.megboyzz.dnevnik.entities.context.marks.init.state.MarksContext
import ru.megboyzz.dnevnik.entities.context.marks.init.state.SchoolType
import ru.megboyzz.dnevnik.entities.db.Credentials
import ru.megboyzz.dnevnik.entities.db.GlobalUserContext
import ru.megboyzz.dnevnik.service.ContextService
import java.net.URL


class AuthorizationManager(dataBase: AppDataBase) {

    private val returnUrl =
        "https://login.dnevnik.ru/oauth2?response_type=token&client_id=bb97b3e445a340b9b9cab4b9ea0dbd6f&scope=CommonInfo,ContactInfo,FriendsAndRelatives,EducationalInfo"

    private val baseUrl = "https://login.dnevnik.ru/login/"

    private val apiUrl = "https://api.dnevnik.ru"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val contextService = retrofit.create(ContextService::class.java)

    private val marksState = "https://dnevnik.ru/marks"

    private val credentialsDao = dataBase.credentialsDao()

    private val userContextDao = dataBase.globalUserContextDao()

    private var credentials = Credentials(token = "", cookies =  mapOf(), rememberMe = false)

    private val gson = Gson()

    fun login(
        login: String,
        password: String,
        rememberMe: Boolean
    ): AuthorizationStatus {

        runCatching<Connection.Response> {
             Jsoup
                .connect(baseUrl)
                .method(Connection.Method.POST)
                .data("ReturnUrl", returnUrl)
                .data("login", login)
                .data("password", password)
                .followRedirects(true)
                .execute()
        }.onFailure {
            return AuthorizationStatus.NO_INTERNET
        }.onSuccess { response ->
            if(response.statusCode() != 200)
                return AuthorizationStatus.MAINTAIN

            val url = response.url()

            if(url.getResult() != "success")
                return AuthorizationStatus.WRONG_CREDENTIALS

            //Безопасная зона
            val cookies = response.cookies()
            credentials = Credentials(
                token = url.getToken(),
                cookies = cookies,
                rememberMe = rememberMe
            )
            dbUpdate()

            kotlin.runCatching {

                Jsoup
                    .connect(marksState)
                    .method(Connection.Method.GET)
                    .cookies(cookies)
                    .ignoreContentType(true)
                    .followRedirects(true)
                    .execute()

            }.onFailure {
                return AuthorizationStatus.NO_INTERNET
            }.onSuccess { response1 ->
                val htmlByLines = response1.body().split("\n")
                val contextLine = htmlByLines.find { it.contains("window.__MARKS__INITIAL__STATE__") }
                val marksContextStr =
                    contextLine?.substring(contextLine.indexOf("{"), contextLine.length - 2)
                val marksContext = gson.fromJson(marksContextStr, MarksContext::class.java)

                val context = contextService.getContext(credentials.token, marksContext.context.userId).execute()



                if(context.isSuccessful){
                    val body = context.body() ?: return AuthorizationStatus.UNKNOWN_ERROR
                    userContextDao.setGlobalUserContext(GlobalUserContext(
                        apiUserContext = body,
                        marksInitStateUserContext = marksContext
                    ))
                } else return AuthorizationStatus.UNKNOWN_ERROR
            }

        }

        return AuthorizationStatus.LOGGED

    }

    fun checkCredentials(): AuthorizationStatus{

        val credentialsIsExists = credentialsDao.credentialsIsExists()
        return if(credentialsIsExists)
            AuthorizationStatus.LOGGED
        else
            AuthorizationStatus.NOTHING

    }

    fun dbUpdate(){
        credentialsDao.setCredentials(credentials)
    }

    fun deleteCredentials(){
        credentialsDao.deleteCredentials()
    }

    fun URL.getResult(): String{

        val httpUrl = this.toString().toHttpUrlOrNull() ?: return ""

        return httpUrl.queryParameter("result") ?: ""

    }

    fun URL.getToken(): String{

        val tokenRegex = "access_token=([A-Za-z0-9]+)"
        val regex = Regex(tokenRegex)
        val find = regex.find(this.toString())
        if(find != null)
            return find.groupValues[1]
        return ""

    }

}