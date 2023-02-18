package ru.megboyzz.dnevnik

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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

//Файл со всеми превьюшками

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