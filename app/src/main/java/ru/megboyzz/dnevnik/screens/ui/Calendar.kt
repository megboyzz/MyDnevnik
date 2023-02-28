package ru.megboyzz.dnevnik.screens.ui

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun MonthToggler(calendarState: CalendarState) {

    //TODO убрать это куда нибудь
    var listOfMonth = listOf(
        R.string.title_january.AsString(),
        R.string.title_february.AsString(),
        R.string.title_march.AsString(),
        R.string.title_april.AsString(),
        R.string.title_may.AsString(),
        R.string.title_june.AsString(),
        R.string.title_july.AsString(),
        R.string.title_august.AsString(),
        R.string.title_september.AsString(),
        R.string.title_october.AsString(),
        R.string.title_november.AsString(),
        R.string.title_december.AsString()
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { calendarState.startMonth = calendarState.startMonth.minusMonths(1) }) {
                Image(
                    painter = R.drawable.ic_month_swipe_left.AsPainter(),
                    contentDescription = "left",
                    alignment = Alignment.Center
                )
            }
            for(style in TextStyle.values())
                Log.i("check", calendarState.startMonth.month.getDisplayName(style, Locale.getDefault()))
            Text(
                //TODO Возможно стоит сделать эту строчку меньше((
                text = "${listOfMonth[calendarState.startMonth.month.ordinal]} ${calendarState.startMonth.year}",
                style = H1,
                color = dark
            )
            IconButton(
                onClick = { calendarState.startMonth = calendarState.startMonth.plusMonths(1) }
            ) {
                Image(
                    painter = R.drawable.ic_month_swipe_right.AsPainter(),
                    contentDescription = "right",
                    alignment = Alignment.Center
                )
            }
        }
    }
}

//TODO доделать свертку календаря, он почти идеально работает
@Composable
fun NiceCalendar(
    startMonth: YearMonth,
    onClick: (day: CalendarDay, month: YearMonth, year: Int) -> Unit
) {
    val endMonth = startMonth.plusMonths(100) // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = startMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val weekState = rememberWeekCalendarState(
        startDate = startMonth.atDay(1),
        endDate = startMonth.atDay(7),
        firstVisibleWeekDate = startMonth.atDay(1),
        firstDayOfWeek = firstDayOfWeek
    )

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column {

        MonthToggler(calendarState = state)

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.defaultMinSize(300.dp)
        ) {
            if(selectedDate == null)
            HorizontalCalendar(
                state = state,
                dayContent = { day ->
                    Day(day, isSelected = selectedDate == day.date) { day ->
                        selectedDate = if (selectedDate == day.date) null else day.date
                        onClick(day, startMonth, startMonth.year)
                    }
                },
                monthHeader = { DaysOfWeekTitle(daysOfWeek = DayOfWeek.values().asList()) },
                modifier = Modifier.padding(10.dp)
            )
            else
            //TODO по возможности устранить повторы
            WeekCalendar(
                state = weekState,
                weekHeader = { _ ->
                    DaysOfWeekTitle(daysOfWeek = DayOfWeek.values().asList())
                },
                dayContent = { day ->
                    Day(CalendarDay(day.date, DayPosition.InDate), isSelected = selectedDate == day.date) { day ->
                        selectedDate = if (selectedDate == day.date) null else day.date
                        onClick(day, startMonth, startMonth.year)
                    }
                },
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase(),
                    style = MainText
                )
            }
        }
        Divider(
            thickness = 1.dp,
            color = dark,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}
@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(ratio = 1f) // This is important for square sizing!
            .clip(CircleShape)
            .background(color = if (isSelected) lightGray else Color.Transparent)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) mainBlue else Color.Transparent
                ),
                shape = CircleShape
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MainText,
            color = if (day.position == DayPosition.MonthDate) dark else Color.Gray
        )
    }
}
