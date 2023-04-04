package ru.megboyzz.dnevnik.screens.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.megboyzz.dnevnik.screens.ui.BaseCard
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.textSchedule

@Composable
fun BaseScheduleCard(
    dayTitle: String,
    subjectNames: List<String>
) {
    BaseCard {
        Column(
            modifier = Modifier.padding(10.dp).padding(bottom = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = dayTitle,
                    style = H2,
                    color = mainBlue,
                )
                Divider(
                    color = mainBlue,
                    thickness = 1.dp,
                )
            }
            for(i in subjectNames.indices){
                Text(
                    text = "${i+1}. ${subjectNames[i]}",
                    style = textSchedule
                )
            }

        }
    }
}