package ru.megboyzz.dnevnik

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import ru.megboyzz.dnevnik.navigation.AppNavHost
import ru.megboyzz.dnevnik.ui.theme.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
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
