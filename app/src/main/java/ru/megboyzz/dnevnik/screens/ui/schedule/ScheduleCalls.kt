package ru.megboyzz.dnevnik.screens.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.screens.ui.BaseCard
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.dark
import java.util.*


//Веремнная сущность для определения пунктов карточки
data class CallData(
    val startTime: String,
    val endTime: String
)

@Composable
fun BaseScheduleCallsCard(
    callsList: List<CallData>
) {
    BaseCard{
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 20.dp).defaultMinSize(300.dp)
        ) {
            for(i in callsList.indices)
                Text(
                    text = "${(i+1).displayTextInRightLocale()} -> ${callsList[i].startTime} - ${callsList[i].endTime}",
                    style = H2,
                    color = dark
                )
        }
    }
}

@Composable
fun Int.displayTextInRightLocale(): String
    = if(Locale.getDefault().country == "RU")
        "$this ${R.string.title_lesson.AsString()}"
    else
        "${R.string.title_lesson.AsString()} $this"
