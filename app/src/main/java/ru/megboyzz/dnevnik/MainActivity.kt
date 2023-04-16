package ru.megboyzz.dnevnik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import ru.megboyzz.dnevnik.navigation.AppNavHost
import ru.megboyzz.dnevnik.ui.theme.lightGray
import java.io.IOException
import java.net.URL

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val login = "kitsekaterina"
        val password = "k12345"
        val returnUrl =
            "https://login.dnevnik.ru/oauth2?response_type=token&client_id=bb97b3e445a340b9b9cab4b9ea0dbd6f&scope=CommonInfo,ContactInfo,FriendsAndRelatives,EducationalInfo"

        val baseUrl = "https://login.dnevnik.ru/login/"

        GlobalScope.launch(Dispatchers.IO) {
            val execute: org.jsoup.Connection.Response = Jsoup
                .connect(baseUrl)
                .method(org.jsoup.Connection.Method.POST)
                .data("ReturnUrl", returnUrl)
                .data("login", login)
                .data("password", password)
                .followRedirects(true).execute()
            val url: URL = execute.url()

            val httpUrl = url.toString().toHttpUrlOrNull()

            val queryParameter = httpUrl?.queryParameter("result")

            println("$httpUrl $queryParameter")
        }

        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = lightGray
            ) {
                AppNavHost()
            }
        }
    }
}
