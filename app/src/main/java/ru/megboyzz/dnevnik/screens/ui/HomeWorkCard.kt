package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.dark
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun BaseHomeWorkCard(
    subjectName: String,
    homeWorkTaskText: String,
    deadlineDate: LocalDate,
    deadlineLesson: Int = -1,
    isDone: Boolean,
    onClickBox: (Boolean) -> Unit
) {
    val deadlineLessonText = if(deadlineLesson != -1) "$deadlineLesson-го урока" else ""

    val format = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    var expanded by remember {
        mutableStateOf(false)
    }

    BaseCard {
        Box(
            modifier = Modifier
                .defaultMinSize(300.dp)
                .padding(5.dp)
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.defaultMinSize(500.dp)
                ){
                    HomeWorkIsDoneCheckBox(isDone = isDone, onClickBox = onClickBox)
                    Text(
                        text = subjectName,
                        style = H2,
                        color = dark,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    HomeWorkCardToggler(expanded = expanded, onChangeExpand = {expanded = !it})
                }
                if(expanded) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(5.dp)
                    ) {
                        HomeworkText(text = homeWorkTaskText)
                        Text(
                            text = "${R.string.title_deadline.AsString()} ${
                                deadlineDate.format(
                                    format
                                )
                            } $deadlineLessonText",
                            style = H2,
                            color = dark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeWorkIsDoneCheckBox(
    isDone: Boolean,
    onClickBox: (Boolean) -> Unit
) {
    val shape = RoundedCornerShape(9.dp)
    Box(
        modifier = Modifier
            .size(25.dp, 20.dp)
            .clip(shape)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = mainBlue
                ),
                shape = shape
            )
            .clickable { onClickBox.invoke(isDone) },
        contentAlignment = Alignment.Center
    ){
        if(isDone)
            Image(
                painter = R.drawable.ic_done.AsPainter(),
                contentDescription = ""
            )
    }
}

@Composable
fun HomeWorkCardToggler(
    expanded: Boolean,
    onChangeExpand: (Boolean) -> Unit
) {
    Box(modifier = Modifier.size(24.dp)) {
        IconButton(
            onClick = { onChangeExpand.invoke(expanded) },
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = (if (expanded) R.drawable.ic_hw_toggled else R.drawable.ic_hw_untoggled).AsPainter(),
                contentDescription = ""
            )
        }
    }
}


@Composable
fun HomeworkText(
    text: String,
){
    Column() {
        Divider(color = dark, thickness = 1.dp, modifier = Modifier.width(500.dp))
        Column(
            modifier = Modifier
                .defaultMinSize(500.dp)
                .height(40.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = dark,
                style = H2,
            )
        }
        Divider(color = dark, thickness = 1.dp, modifier = Modifier.width(500.dp))
    }
}
