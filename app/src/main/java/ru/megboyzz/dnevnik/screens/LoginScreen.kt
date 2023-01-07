package ru.megboyzz.dnevnik.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.SpacerHeight
import ru.megboyzz.dnevnik.SpacerWidth
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {

    val login = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val rememberMe = remember {
        mutableStateOf(false)
    }

    LoginScreenContent(
        login = login,
        password = password,
        isError = isError,
        isRememberMe = rememberMe,
        onSignIn = { navController.navigate(AppNavRoute.Marks.route) },
        onSignUp = { /*TODO*/ },
        onForgetPassword = { /* TODO */ }
    )
}
