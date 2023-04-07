package ru.megboyzz.dnevnik.screens.ui

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kizitonwose.calendar.core.daysOfWeek
import ru.megboyzz.dnevnik.screens.SettingsScreen
import ru.megboyzz.dnevnik.screens.ui.*
import ru.megboyzz.dnevnik.screens.ui.calendar.NiceCalendar
import ru.megboyzz.dnevnik.screens.ui.main.SettingsScaffold
import ru.megboyzz.dnevnik.screens.ui.piechart.PieChart
import ru.megboyzz.dnevnik.screens.ui.piechart.PieChartCard
import ru.megboyzz.dnevnik.screens.ui.piechart.PieChartData
import ru.megboyzz.dnevnik.screens.ui.schedule.BaseScheduleCallsCard
import ru.megboyzz.dnevnik.screens.ui.schedule.BaseScheduleCard
import ru.megboyzz.dnevnik.screens.ui.schedule.BaseScheduleShield
import ru.megboyzz.dnevnik.screens.ui.schedule.CallData
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

//Файл со всеми превьюшками

@Composable
fun NicePreviewContainer(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .height(1500.dp),
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
fun NiceCalendarPrev() {

    var startMonth by remember{
        mutableStateOf(YearMonth.now())
    }

    NiceCalendar(
        currentMonth = startMonth,
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


@Preview
@Composable
fun MessagesPrev() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MessageAlert {
            EmptyMessage(message = "Таких оценок нет((")
        }
        MessageAlert {
            FineMessage(message = "Сегодня нет уроков!")
        }
    }

}

@Preview
@Composable
fun HomeWorkCardPrev() {
    var isDone by remember {
        mutableStateOf(false)
    }
    BaseHomeWorkCard(
        subjectName = "Алгебра и начала математического анализа",
        homeWorkTaskText = "C. 45 №45, №56, №46",
        deadlineDate = LocalDate.now(),
        isDone = isDone,
        onClickBox = {
            isDone = !it
        }
    )
}


@Preview
@Composable
fun ScheduleShieldPrev() {
    BaseScheduleShield(
        subjectName = "Физика",
        teacherName = "Иванов Иван Иванович",
        classNumber = "3.13",
        nextLessonName = "Обществознание"
    )
}

@Preview
@Composable
fun ScheduleCardPrev() {

    val daysOfWeek = daysOfWeek().dropLast(1)
    Log.i("Locale", Locale.getDefault().country)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(daysOfWeek){
            BaseScheduleCard(
                dayTitle = it.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                subjectNames = listOf(
                    "Литература",
                    "Алгебра",
                    "Геометрия",
                    "Алгебра и начала математического анализа",
                    "Физика",
                    "Астрономия",
                )
            )
        }
    }
}

@Preview
@Composable
fun ScheduleCallsPrev() {
    val calls = listOf(
        CallData("9:00", "10:30"),
        CallData("10:40", "12:10"),
        CallData("12:40", "14:10"),
        CallData("14:20", "15:50"),
        CallData("18:00", "19:30")
    )
    
    BaseScheduleCallsCard(callsList = calls)
    
}

@Preview
@Composable
fun ScaffoldTest() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    SettingsScaffold(
        onLeave = { /*TODO*/ },
        scaffoldState = scaffoldState,
        navController = navController
    ) {

    }
}

@Preview
@Composable
fun SettingsTest() {

    SettingsScreen(navController = rememberNavController(), scaffoldState = rememberScaffoldState())

}

@Preview
@Composable
fun Test1() {

    AlertMessageBox(
        title = "Информация",
        alertText = "В данный момент на сервере ведутся технические работы, вход невозможен, повторите попытку позднее",
        onClick = { /*TODO*/ }
    )

}