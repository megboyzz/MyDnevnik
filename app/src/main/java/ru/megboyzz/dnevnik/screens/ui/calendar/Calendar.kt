package ru.megboyzz.dnevnik.screens.ui.calendar

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.MonthDay
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

//TODO доделать свертку календаря, он почти идеально работает
@Composable
fun NiceCalendar(
    currentMonth: YearMonth,
    onClick: (day: CalendarDay, month: YearMonth, year: Int) -> Unit
) {

    val selections = remember { mutableStateListOf<LocalDate>() }
    val daysOfWeek = remember { daysOfWeek() }
    var transitionEnabled by remember { mutableStateOf(true) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    val weekState = rememberWeekCalendarState(
        startDate = currentMonth.atDay(1),
        endDate = currentMonth.atDay(7),
        firstVisibleWeekDate = currentMonth.atDay(1),
        firstDayOfWeek = daysOfWeek.first()
    )

    var selectedWeek by remember { mutableStateOf<Week?>(null) }

    val monthScrollCoroutineScope = rememberCoroutineScope()
    val weekScrollCoroutineScope = rememberCoroutineScope()

    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

    Column {

        SimpleCalendarTitle(
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                state.scrollToPreviousMonth(monthScrollCoroutineScope)
            },
            goToNext = {
                state.scrollToNextMonth(monthScrollCoroutineScope)
            },
            transitionEnabled = transitionEnabled
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.defaultMinSize(300.dp)
        ) {
            if(selectedWeek == null) {
                transitionEnabled = true
                HorizontalCalendar(
                    state = state,
                    dayContent = { day ->

                        MonthDay(
                            day = day,
                            isSelected = selections.contains(day.date)
                        ) { clickedDay ->
                            Log.i("Click!", "MonthDay is Clicked!")
                            //Чтобы два раза не вызывать week()
                            val week = clickedDay.week()

                            if(!selections.contains(day.date)) {
                                selections.removeIf { it != day.date }
                                selections.add(day.date)
                            }else selections.remove(day.date)

                            weekScrollCoroutineScope.launch {
                                weekState.startDate = week.days.first().date
                                weekState.endDate = week.days.last().date
                                weekState.animateScrollToWeek(clickedDay.date)
                            }

                            selectedWeek = if (selectedWeek == week) null else week
                            onClick(clickedDay, currentMonth, currentMonth.year)
                        }
                    },
                    monthHeader = { DaysOfWeekTitle(daysOfWeek = DayOfWeek.values().asList()) },
                    modifier = Modifier.padding(10.dp)
                )
            }
            else {
                transitionEnabled = false
                WeekCalendar(
                    state = weekState,
                    weekHeader = { _ ->
                        DaysOfWeekTitle(daysOfWeek = DayOfWeek.values().asList())
                    },
                    dayContent = { day ->
                        WeekDay(
                            day = day,
                            isSelected = selections.contains(day.date)
                        ) { calendarDay ->
                            Log.i("Click!", "WeekDay is Clicked!")
                            if(!selections.contains(day.date)) {
                                selections.removeIf { it != day.date }
                                selections.add(day.date)
                            }else selections.remove(day.date)
                            selectedWeek = null

                        }
                    },
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}


fun CalendarState.scrollToPreviousMonth(coroutineScope: CoroutineScope){
    val it = this
    coroutineScope.launch {
        it.scrollToMonth(it.firstVisibleMonth.yearMonth.previousMonth)
    }
}

fun CalendarState.scrollToNextMonth(coroutineScope: CoroutineScope){
    val it = this
    coroutineScope.launch {
        it.scrollToMonth(it.firstVisibleMonth.yearMonth.nextMonth)
    }
}


@Composable
fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) = Box(
    modifier = Modifier
        .size(35.dp)
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick, enabled = enabled),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription,
    )
}


@Composable
fun SimpleCalendarTitle(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
    transitionEnabled: Boolean = true
) {

    val listOfMonth = listOf(
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier
                .height(40.dp)
                .padding(vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CalendarNavigationIcon(
                icon = R.drawable.ic_month_swipe_left.AsPainter(),
                contentDescription = "Previous",
                onClick = goToPrevious,
                enabled = transitionEnabled
            )
            Text(
                text = currentMonth.displayText(listOfMonth),
                style = H1,
                color = dark
            )
            CalendarNavigationIcon(
                icon = R.drawable.ic_month_swipe_right.AsPainter(),
                contentDescription = "Next",
                onClick = goToNext,
                enabled = transitionEnabled
            )
        }
    }
}



fun YearMonth.displayText(list: List<String>): String {
    return "${list[this.month.ordinal]} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL_STANDALONE
    return getDisplayName(style, Locale.getDefault())
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.getDefault()).let { value ->
        if (uppercase) value.uppercase(Locale.getDefault()) else value
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
fun MonthDay(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Day(day = day, isSelected = isSelected, onClick = onClick)
}

@Composable
fun WeekDay(
    day: WeekDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Log.i("WeekDay", day.toString())
    val position = when(day.position){
        WeekDayPosition.InDate -> DayPosition.MonthDate
        WeekDayPosition.OutDate -> DayPosition.MonthDate
        WeekDayPosition.RangeDate -> DayPosition.MonthDate
    }
    val newDay = CalendarDay(day.date, position)
    Day(day = newDay, isSelected = isSelected, onClick = onClick)
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Log.i("BaseDay", day.toString())
    Box(
        modifier = Modifier
            .aspectRatio(ratio = 1f) // This is important for square sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(color = if (isSelected) lightGray else Color.Transparent)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) mainBlue else Color.Transparent
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MainText,
                textAlign = TextAlign.Justify,
                color = if (day.position == DayPosition.MonthDate) dark else Color.Gray,
            )
        }
    }
}
