package ru.megboyzz.dnevnik.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.screens.ui.main.MainScaffold

@Composable
fun HomeWorkScreen(
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    MainScaffold(
        icon = R.drawable.ic_homework.AsPainter(),
        title = R.string.title_hw.AsString(),
        navController = navController,
        scaffoldState = scaffoldState
    ) {

    }
}