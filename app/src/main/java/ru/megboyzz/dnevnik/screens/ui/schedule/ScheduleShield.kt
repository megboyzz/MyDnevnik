package ru.megboyzz.dnevnik.screens.ui.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.screens.ui.BaseCard
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.MainText
import ru.megboyzz.dnevnik.ui.theme.dark
import ru.megboyzz.dnevnik.ui.theme.mainBlue

@Composable
fun BaseScheduleShield(
    subjectName: String,
    teacherName: String,
    classNumber: String,
    nextLessonName: String
) {
    BaseCard {
        Column(
            modifier = Modifier
                .defaultMinSize(300.dp)
                .padding(top = 20.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = R.drawable.ic_cap.AsPainter(),
                    contentDescription = ""
                )

                TextHeader(
                    title = R.string.title_now_lesson.AsString(),
                    value = subjectName
                )
            }
            TextHeader(
                title = R.string.title_lesson_teacher.AsString(),
                value = teacherName
            )
            TextHeader(
                title = R.string.title_classroom.AsString(),
                value = classNumber
            )
            TextHeader(
                title = R.string.title_next_lesson.AsString(),
                value = nextLessonName,
                valueIsBlue = false
            )
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
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.width(200.dp)
    ) {
        Text(
            text = title,
            style = MainText,
            color = dark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = value,
            style = H2,
            color = if(valueIsBlue) mainBlue else dark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}