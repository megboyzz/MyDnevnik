package ru.megboyzz.dnevnik.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


sealed class AppNavRoute(val route: String){

    object Splash : AppNavRoute("splash_screen")

    object Login: AppNavRoute("login_screen")
    object Marks: AppNavRoute("marks_screen")
    object Schedule: AppNavRoute("schedule_screen")
    object HomeWorks: AppNavRoute("hw_screen")
    object Settings: AppNavRoute("settings_screen")
}

@Composable
fun AppNavHost(){
    val navController = rememberNavController()

    /*val activity = LocalContext.current as MainActivity

    val application = activity.application as App

    val viewModel: AppViewModel = viewModel(factory = AppViewModelFactory(application))*/

    NavHost(
        navController = navController,
        startDestination = AppNavRoute.Splash.route
    ){
        composable(AppNavRoute.Splash.route)        {  }
        composable(AppNavRoute.Marks.route)         {  }
        composable(AppNavRoute.Schedule.route)      {  }
        composable(AppNavRoute.HomeWorks.route)     {  }
        composable(AppNavRoute.Settings.route)      {  }
    }
}