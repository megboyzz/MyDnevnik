package ru.megboyzz.dnevnik

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import ru.megboyzz.dnevnik.navigation.AppNavHost
import ru.megboyzz.dnevnik.ui.theme.lightGray
import ru.megboyzz.dnevnik.viewmodel.AuthorizationViewModel
import ru.megboyzz.dnevnik.viewmodel.AuthorizationViewModelFactory
import java.io.IOException
import java.net.URL

class MainActivity : ComponentActivity() {

    lateinit var app: App


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.app = application as App

        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = lightGray
            ) {
                AppNavHost()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDestroy() {
        super.onDestroy()

        GlobalScope.launch(Dispatchers.IO) {

            val credentialsDao = app.database.credentialsDao()
            val globalUserContext = app.database.globalUserContextDao()

            if(credentialsDao.credentialsIsExists()) {
                val credentials = credentialsDao.getCredentials()
                if (!credentials.rememberMe) {
                    credentialsDao.deleteCredentials()
                    globalUserContext.deleteGlobalUserContext()
                }
            }


        }

    }
}
