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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

//TODO доделать свертку календаря, он почти идеально работает
@Composable
fun NiceCalendar(
    currentMonth: YearMonth,
    onClick: (day: CalendarDay, month: YearMonth, year: Int) -> Unit
) {

    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }

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

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

    Column {

        SimpleCalendarTitle(
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            }
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.defaultMinSize(300.dp)
        ) {
            if(selectedDate == null) {
                HorizontalCalendar(
                    state = state,
                    dayContent = { day ->
                        Day(day, isSelected = selectedDate == day.date) { day ->
                            selectedDate = if (selectedDate == day.date) null else day.date
                            onClick(day, currentMonth, currentMonth.year)
                        }
                    },
                    monthHeader = { DaysOfWeekTitle(daysOfWeek = DayOfWeek.values().asList()) },
                    modifier = Modifier.padding(10.dp)
                )
            }
            else {
                //TODO по возможности устранить повторы
                WeekCalendar(
                    state = weekState,
                    weekHeader = { _ ->
                        DaysOfWeekTitle(daysOfWeek = DayOfWeek.values().asList())
                    },
                    dayContent = { day ->
                        Day(
                            CalendarDay(day.date, DayPosition.InDate),
                            isSelected = selectedDate == day.date
                        ) { calendarDay ->
                            selectedDate = if (selectedDate == calendarDay.date) null else calendarDay.date
                            onClick(calendarDay, currentMonth, currentMonth.year)
                        }
                    },
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}


@Composable
fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .size(35.dp)
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
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
        modifier = Modifier.fillMaxWidth().padding(10.dp),
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
