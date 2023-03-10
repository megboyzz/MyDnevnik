package ru.megboyzz.dnevnik

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ru.megboyzz.dnevnik.screens.ui.*
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white
import java.time.YearMonth
import java.util.*

//Файл со всеми превьюшками

@Composable
fun NicePreviewContainer(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()).height(1500.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) { content() }
}

@Preview
@Composable
fun text() {
    Row(Modifier.fillMaxWidth()) {
        AlmostOutlinedText(text = "Здарова карова")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun DrawerPrev() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberAnimatedNavController()
    Box(Modifier.background(mainBlue)) {
        DrawerContent(navController = navController, scaffoldState = scaffoldState)
    }

}


@Preview
@Composable
fun CardWithAveragePrev() {
    CardWithAverage(
        averageMark = 4.33f,
        resultMark = 4f
    ) {
        Box(Modifier.padding(10.dp)){
            Text("hehe")
        }
    }
}

@Preview
@Composable
fun BaseCardPrev() {
    Box(
        Modifier
            .fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            Card(
                backgroundColor = white,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = mainBlue
                ),
                modifier = Modifier.defaultMinSize(minWidth = 300.dp)
            ) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.Red)){}
            }
        }
    }
}

@Preview
@Composable
fun MonthTglPrev() {
    MonthToggle(
        startMonth = Month.January, startYear = 2023
    ){

    }
}

@Preview
@Composable
fun UnderlinedTextPrev() {
    Box(Modifier.background(white)){
        UnderlinedText(text = "Общая сводка")
    }
}

@Preview
@Composable
fun CirclePrev() {
    val list = listOf(
        PieChartData(legend = "История", value=5),
        PieChartData(legend = "Русский язык", value=4),
        PieChartData(legend = "Химия", value=1),
        PieChartData(legend = "Математика", value=3)
    )
    PieChart(list)
}

@Preview
@Composable
fun GoodPieChartPrev() {
    val list = listOf(
        PieChartData(legend = "История", value=5),
        PieChartData(legend = "Русский язык", value=4),
        PieChartData(legend = "Химия", value=1),
        PieChartData(legend = "Математика", value=3),
    )
    PieChart(diagramData = list)
}

@Preview
@Composable
fun PieChartCardPrev() {
    val list = listOf(
        PieChartData(legend = "История", value=5),
        PieChartData(legend = "Русский язык", value=4),
        PieChartData(legend = "Химия", value=1),
        PieChartData(legend = "Математика", value=3),
    )
    PieChartCard(
        title = "Сводка по оценкам отлично",
        data = list
    )
}

@Preview
@Composable
fun LastMarkCardPrev() {
    LastMarkCard(
        subjectName = "Русский язык",
        mark = 5,
        date = "18.02.2023",
        teacher = "Степанова А.С.",
        reason = "контрольная"
    )
}

@Preview
@Composable
fun AllMarksCardPrev1() {
    NicePreviewContainer {
        AllMarksCard(
            subjectName = "Математика",
            teacher = "Степанова С.А.",
            marks = listOf(4, 5, 5, 5, 5, 5, 4, 5, 3, 2),
            resultMark = 4f
        )
        AllMarksCard(
            subjectName = "Русский язык",
            teacher = "Степанова С.А.",
            marks = listOf(4, 5, 5, 5, 5, 5, 4, 5, 3, 2),
            resultMark = null
        )
        AllMarksCard(
            subjectName = "Русский язык",
            teacher = "Степанова С.А.",
            marks = null,
            resultMark = 4.2f
        )
        AllMarksCard(
            subjectName = "Русский язык",
            teacher = null,
            marks = listOf(4, 5, 5, 5, 5, 5, 4, 5, 3, 2),
            resultMark = 4.2f
        )

        AllMarksCard(
            subjectName = "Математика",
            teacher = "Степанова С.А.",
            marks = null,
            resultMark = null
        )

        AllMarksCard(
            subjectName = "Математика",
            teacher = null,
            marks = null,
            resultMark = null
        )
    }
}

@Preview
@Composable
fun NewCalendarPrev() {
    NewCardCalendar(
        month = Month.January,
        year = 2023
    ){

    }
}

@Preview
@Composable
fun NiceCalendarPrev() {
    NiceCalendar(
        startMonth = YearMonth.now()
    ){ day, month, year ->

    }
}

@Preview
@Composable
fun EmptyPrev() {
    ContentMessage(message = "Таких оценок нет")
}

@Preview
@Composable
fun PieChartNullPrev() {
    val list = listOf(
        PieChartData(legend = "История", value=5),
        PieChartData(legend = "Русский язык", value=4),
        PieChartData(legend = "Химия", value=1),
        PieChartData(legend = "Математика", value=3),
    )
    NicePreviewContainer {
        PieChartCard(
            title = "Сводка по оценкам отлично",
            data = list
        )
        PieChartCard(
            title = "Сводка по оценкам хорошо",
            data = null
        )
    }
}
