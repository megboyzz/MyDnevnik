package ru.megboyzz.dnevnik.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.screens.ui.LoginScreenContent

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
