package ru.megboyzz.dnevnik.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.screens.ui.MainScaffold

@Composable
fun ScheduleScreen(
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    MainScaffold(
        icon = R.drawable.ic_schedule.AsPainter(),
        title = R.string.title_schedule.AsString(),
        navController = navController,
        scaffoldState = scaffoldState
    ) {

    }
}