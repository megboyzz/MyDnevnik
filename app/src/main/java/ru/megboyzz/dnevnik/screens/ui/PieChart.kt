package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.dark
import ru.megboyzz.dnevnik.R

/**
 * Класс для предоставления данных для круговых диаграм
 * Идея предоставить диаграмме массив именованных данных
 * напрмер кол-во оцеок отлично по всем предметам:
 *  CircleDiagramData(parameterName = "История", count=5) = 38%
 *  CircleDiagramData(parameterName = "Русский язык", count=4) = 31%
 *  CircleDiagramData(parameterName = "Химия", count=1) = 8%
 *  CircleDiagramData(parameterName = "Математика", count=3) = 23%
 */
data class PieChartData(
    val legend: String,
    val value: Int
){
    val color: Color
    init {
        val hue = (0..360).random(kotlin.random.Random(System.nanoTime())).toFloat()
        Thread.sleep(10)
        color = Color.hsl(hue, 0.5f, 0.6f)
    }
}

@Composable
fun PieChart(
    diagramData: List<PieChartData>
) {
    lateinit var chart: DataPieChart
    Column(Modifier.size(200.dp)) {
        AndroidView(
            factory = { context ->
                chart = DataPieChart(context)
                chart
            },
            modifier = Modifier
                .wrapContentSize()
                .padding(5.dp),
            update = { chart.updatePieChartWithData(diagramData) }
        )
    }
}


@Composable
fun PieChartCard(
    title: String,
    data: List<PieChartData>?
) {
    BaseCard {
        Column(
            modifier = Modifier.padding(15.dp, 10.dp),
        ){
            Text(
                text = title,
                style = H2,
                color = dark
            )
            Box(
                modifier = Modifier.defaultMinSize(300.dp),
                contentAlignment = Alignment.Center
            ){
                if(data != null)
                    PieChart(diagramData = data)
                else
                    EmptyMessage(message = R.string.title_no_marks.AsString())
            }
        }
    }
}