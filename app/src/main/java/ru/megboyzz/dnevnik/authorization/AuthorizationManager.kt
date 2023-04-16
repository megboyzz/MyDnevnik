package ru.megboyzz.dnevnik.authorization

import android.util.Log
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Connection
import org.jsoup.Jsoup
import ru.megboyzz.dnevnik.db.dao.CredentialsDao
import ru.megboyzz.dnevnik.entities.db.Credentials
import java.net.URL


class AuthorizationManager(private val credentialsDao: CredentialsDao) {

    private val returnUrl =
        "https://login.dnevnik.ru/oauth2?response_type=token&client_id=bb97b3e445a340b9b9cab4b9ea0dbd6f&scope=CommonInfo,ContactInfo,FriendsAndRelatives,EducationalInfo"

    private val baseUrl = "https://login.dnevnik.ru/login/"

    private var credentials = Credentials(token = "", cookies =  mapOf(), rememberMe = false)

    fun login(
        login: String,
        password: String,
        rememberMe: Boolean
    ): AuthorizationStatus {

        val execute = runCatching<Connection.Response> {
             Jsoup
                .connect(baseUrl)
                .method(Connection.Method.POST)
                .data("ReturnUrl", returnUrl)
                .data("login", login)
                .data("password", password)
                .followRedirects(true)
                .execute()
        }.onFailure {

            Log.i("AuthorizationManager", "No internet")
            return AuthorizationStatus.NO_INTERNET
        }

        val response = execute.getOrNull()

        if(response != null){

            if(response.statusCode() != 200) {
                Log.i("AuthorizationManager", "Maintain")
                return AuthorizationStatus.MAINTAIN
            }

            val url = response.url()

            if(url.getResult() != "success") {
                Log.i("AuthorizationManager", "Wrong login")
                return AuthorizationStatus.WRONG_CREDENTIALS
            }

            //Безопасная зона
            val cookies = response.cookies()
            credentials = Credentials(
                token = url.getToken(),
                cookies = cookies,
                rememberMe = rememberMe
            )
            dbUpdate()

        }else return AuthorizationStatus.NO_INTERNET //Может быть опасно

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