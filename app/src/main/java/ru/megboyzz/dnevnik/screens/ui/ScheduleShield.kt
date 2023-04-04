package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun BaseScheduleShield(
    subjectName: String,
    teacherName: String,
    classNumber: String,
    nextLessonName: String
) {
    BaseCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        }
    }
}

@Composable
fun TextHeader(
    title: String,
    value: String,
    valueIsBlue: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

    }
}