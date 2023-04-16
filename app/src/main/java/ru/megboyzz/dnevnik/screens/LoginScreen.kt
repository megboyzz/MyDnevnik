package ru.megboyzz.dnevnik.screens

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.MainActivity
import ru.megboyzz.dnevnik.authorization.AuthorizationStatus
import ru.megboyzz.dnevnik.collectAsMutableState
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.screens.ui.AlertMessageBox
import ru.megboyzz.dnevnik.screens.ui.LoginScreenContent
import ru.megboyzz.dnevnik.viewmodel.AuthorizationViewModel
import ru.megboyzz.dnevnik.viewmodel.AuthorizationViewModelFactory

//TODO Переверстать экран входа
@Composable
fun LoginScreen(navController: NavController) {

    val app = (LocalContext.current as MainActivity).app

    val authorizationViewModel: AuthorizationViewModel = viewModel(
        factory = AuthorizationViewModelFactory(app)
    )

    val login = authorizationViewModel.login.collectAsMutableState()

    val password = authorizationViewModel.password.collectAsMutableState()

    val loginStatus = authorizationViewModel.loginStatus.collectAsMutableState()

    val rememberMe = authorizationViewModel.rememberMe.collectAsMutableState()

    var isError by remember{
        mutableStateOf(true)
    }

    if(loginStatus.value == AuthorizationStatus.WRONG_CREDENTIALS && isError) {
        AlertMessageBox(
            title = "Информация",
            alertText = "Неверный логин или пароль",
            onClick = { isError =! isError }
        )
    }

    if(loginStatus.value == AuthorizationStatus.LOGGED)
        navController.navigate(AppNavRoute.Marks.route)

    LoginScreenContent(
        login = login.value,
        onLoginChange = { login.value = it },

        password = password.value,
        onPasswordChange = { password.value = it },

        isError = loginStatus.value == AuthorizationStatus.WRONG_CREDENTIALS,
        onErrorChange = {  },

        isLoading = loginStatus.value == AuthorizationStatus.LOGGING,

        isRememberMe = rememberMe.value,
        onRememberChange = { rememberMe.value = it },

        onSignIn = {
            if(loginStatus.value != AuthorizationStatus.LOGGED)
                authorizationViewModel.login()
        },
        onSignUp = { /*TODO*/ },
        onForgetPassword = { /* TODO */ }
    )
}
