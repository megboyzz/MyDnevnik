package ru.megboyzz.dnevnik.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.navigation.MarksSubScreenNavHost
import ru.megboyzz.dnevnik.screens.ui.MainScaffold

@Composable
fun MarksScreen(
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    MainScaffold(
        icon = R.drawable.ic_marks.AsPainter(),
        title = R.string.title_marks.AsString(),
        navController = navController,
        scaffoldState = scaffoldState
    ) {
        MarksSubScreenNavHost()
    }

}