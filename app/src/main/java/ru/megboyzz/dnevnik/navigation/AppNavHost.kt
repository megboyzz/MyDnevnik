package ru.megboyzz.dnevnik.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.MainActivity
import ru.megboyzz.dnevnik.authorization.AuthorizationStatus
import ru.megboyzz.dnevnik.collectAsMutableState
import ru.megboyzz.dnevnik.screens.*
import ru.megboyzz.dnevnik.viewmodel.AuthorizationViewModel
import ru.megboyzz.dnevnik.viewmodel.AuthorizationViewModelFactory

sealed class BaseNavRote(open val route: String)

sealed class AppNavRoute(override val route: String): BaseNavRote(route){

    object Splash : AppNavRoute("splash_screen")

    object Login: AppNavRoute("login_screen")
    object Marks: AppNavRoute("marks_screen")
    object Schedule: AppNavRoute("schedule_screen")
    object HomeWorks: AppNavRoute("hw_screen")
    object Settings: AppNavRoute("settings_screen")
}

@Composable
fun AppNavHost(){

    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    val app = (LocalContext.current as MainActivity).app

    val authorizationViewModel: AuthorizationViewModel = viewModel(
        factory = AuthorizationViewModelFactory(app)
    )

    val loginStatus = authorizationViewModel.loginStatus.collectAsMutableState()

    NavHost(
        navController = navController,
        startDestination = AppNavRoute.Splash.route
    ){
        composable(AppNavRoute.Splash.route)        {

            if(loginStatus.value != AuthorizationStatus.LOGGED){
                navController.navigate(AppNavRoute.Login.route)
            }else
                navController.navigate(AppNavRoute.Marks.route)


        }
        composable(AppNavRoute.Login.route)         { LoginScreen(navController) }
        composable(AppNavRoute.Marks.route)         { MarksScreen(navController, scaffoldState) }
        composable(AppNavRoute.Schedule.route)      { ScheduleScreen(navController, scaffoldState) }
        composable(AppNavRoute.HomeWorks.route)     { HomeWorkScreen(navController, scaffoldState) }
        composable(AppNavRoute.Settings.route)      { SettingsScreen(navController, scaffoldState) }
    }
}