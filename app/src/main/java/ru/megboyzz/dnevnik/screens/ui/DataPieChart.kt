package ru.megboyzz.dnevnik.screens.ui

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import ru.megboyzz.dnevnik.ui.theme.MainText

class DataPieChart(context: Context) : PieChart(context) {
    
    init {
        layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, 
            LayoutParams.MATCH_PARENT,
        )
        setUsePercentValues(true)
        description.isEnabled = false
        isDrawHoleEnabled = false
        
        legend.isEnabled = false
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.horizontalAlignment =
            Legend.LegendHorizontalAlignment.CENTER
        legend.textSize = 10f

        setEntryLabelColor(Color.Black.toArgb())
    }

    fun updatePieChartWithData(data: List<PieChartData>) {
        val entries = data.map { PieEntry(it.value.toFloat(), it.legend) }

        val ds = PieDataSet(entries, "")

        ds.colors = data.map { it.color.toArgb() }

        ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

        ds.sliceSpace = 2f

        ds.valueTextColor = Color.Black.toArgb()
        ds.valueFormatter = PercentFormatter(this)
        ds.valueTextSize = 10f
        ds.valueTypeface = Typeface.DEFAULT

        this.data = PieData(ds)

        invalidate()
    }
    
}