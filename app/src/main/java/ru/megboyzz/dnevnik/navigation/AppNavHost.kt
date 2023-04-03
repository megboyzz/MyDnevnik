package ru.megboyzz.dnevnik.navigation

import android.window.SplashScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ru.megboyzz.dnevnik.screens.HomeWorkScreen
import ru.megboyzz.dnevnik.screens.LoginScreen
import ru.megboyzz.dnevnik.screens.MarksScreen
import ru.megboyzz.dnevnik.screens.ScheduleScreen

sealed class BaseNavRote(open val route: String)

sealed class AppNavRoute(override val route: String): BaseNavRote(route){

    object Splash : AppNavRoute("splash_screen")

    object Login: AppNavRoute("login_screen")
    object Marks: AppNavRoute("marks_screen")
    object Schedule: AppNavRoute("schedule_screen")
    object HomeWorks: AppNavRoute("hw_screen")
    object Settings: AppNavRoute("settings_screen")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(){

    val scaffoldState = rememberScaffoldState()
    val navController = rememberAnimatedNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavRoute.Login.route /* сделать проверку куки файлов */
    ){
        composable(AppNavRoute.Splash.route)        {  }
        composable(AppNavRoute.Login.route)         { LoginScreen(navController) }
        composable(AppNavRoute.Marks.route)         { MarksScreen(navController, scaffoldState) }
        composable(AppNavRoute.Schedule.route)      { ScheduleScreen(navController, scaffoldState) }
        composable(AppNavRoute.HomeWorks.route)     { HomeWorkScreen(navController, scaffoldState) }
        composable(AppNavRoute.Settings.route)      {  }
    }
}