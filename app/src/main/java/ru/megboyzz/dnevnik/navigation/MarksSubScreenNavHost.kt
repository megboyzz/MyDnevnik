package ru.megboyzz.dnevnik.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.screens.*
import ru.megboyzz.dnevnik.screens.ui.SubScreenData
import ru.megboyzz.dnevnik.screens.ui.SubScreenNavBar

sealed class MarksNavRoute(override val route: String): BaseNavRote(route){

    object LastMarks:       MarksNavRoute("last_marks")
    object ByDaysMarks:     MarksNavRoute("by_days_marks")
    object StatsByMarks:    MarksNavRoute("stats_by_marks")
    object StatsBySubjects: MarksNavRoute("stats_by_subjects")
    object ByTermsMarks:    MarksNavRoute("by_terms_marks")
    object ByYearMarks:     MarksNavRoute("by_terms_marks")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MarksSubScreenNavHost() {
    
    val navController = rememberAnimatedNavController()

    val list = listOf(
        SubScreenData(
            R.string.title_last_marks.AsString(),
            MarksNavRoute.LastMarks,
            remember { mutableStateOf(true) }
        ),
        SubScreenData(
            R.string.title_by_days.AsString(),
            MarksNavRoute.ByDaysMarks,
            remember { mutableStateOf(false) }
        ),
        SubScreenData(
            R.string.title_statistics_by_marks.AsString(),
            MarksNavRoute.StatsByMarks,
            remember { mutableStateOf(false) }
        ),
        SubScreenData(
            R.string.title_statistics_by_subjects.AsString(),
            MarksNavRoute.StatsBySubjects,
            remember { mutableStateOf(false) }
        ),
        SubScreenData(
            R.string.title_by_terms_4.AsString(),
            MarksNavRoute.ByTermsMarks,
            remember { mutableStateOf(false) }
        ),
        SubScreenData(
            R.string.title_one_year.AsString(),
            MarksNavRoute.ByYearMarks,
            remember { mutableStateOf(false) }
        ),
    )
    Column {
        SubScreenNavBar(list = list, subScreenNavController = navController)

        NavHost(
            navController = navController,
            startDestination = MarksNavRoute.LastMarks.route
        ){
            composable(MarksNavRoute.LastMarks.route)           { Text(MarksNavRoute.LastMarks.route) }
            composable(MarksNavRoute.ByDaysMarks.route)         { Text(MarksNavRoute.ByDaysMarks.route) }
            composable(MarksNavRoute.StatsByMarks.route)        { Text(MarksNavRoute.StatsByMarks.route) }
            composable(MarksNavRoute.StatsBySubjects.route)     { Text(MarksNavRoute.StatsBySubjects.route) }
            composable(MarksNavRoute.ByTermsMarks.route)        { Text(MarksNavRoute.ByTermsMarks.route) }
            composable(MarksNavRoute.ByYearMarks.route)         { Text(MarksNavRoute.ByYearMarks.route) }
        }
    }

}