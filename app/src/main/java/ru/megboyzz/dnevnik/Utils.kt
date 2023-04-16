package ru.megboyzz.dnevnik


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarLayoutInfo
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.flow.filterNotNull
import ru.megboyzz.dnevnik.navigation.BaseNavRote
import ru.megboyzz.dnevnik.ui.theme.dark
import java.time.DayOfWeek

@Composable
fun Int.AsPainter() = painterResource(this)

@Composable
fun Int.AsString() = stringResource(this)

@Composable
fun SpacerWidth(width: Dp) = Spacer(Modifier.width(width))

@Composable
fun SpacerHeight(height: Dp) = Spacer(Modifier.height(height))


@Composable
fun Int.AsImageVector() = ImageVector.vectorResource(this)

fun NavController.navigate(to: BaseNavRote){
/*
    LaunchedEffect(Unit){
        navigate(to.route) {
            popUpTo(to.route) { inclusive = true }
        }
    }*/

    this.navigate(to.route){
        launchSingleTop = true
    }

}

fun Modifier.drawColoredShadow(
    color: Color = Color.Black,
    alpha: Float = 0.07f,
    borderRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 7.dp,
    spread: Dp = 0.dp,
    enabled: Boolean = true,
) = if(enabled) {
    this.drawBehind {
        val transparentColor = color.copy(alpha = 0.0f).toArgb()
        val shadowColor = color.copy(alpha = alpha).toArgb()
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                blurRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.save()

            if(spread.value > 0) {
                fun calcSpreadScale(spread: Float, childSize: Float): Float {
                    return 1f + ((spread / childSize) * 2f)
                }

                it.scale(
                    calcSpreadScale(spread.toPx(), this.size.width),
                    calcSpreadScale(spread.toPx(), this.size.height),
                    this.center.x,
                    this.center.y
                )
            }

            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
            it.restore()
        }
    }
} else this

fun Modifier.mainClickable(
    radius: Dp = Dp.Unspecified,
    onClick: () -> Unit
) = composed { this.clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = rememberRipple(
        radius = radius,
        color = dark
    ),
    onClick = onClick
) }

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}


@Composable
fun rememberFirstMostVisibleWeek(
    state: WeekCalendarState,
    viewportPercent: Float = 50f,
): Week {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleWeek) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleWeek(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}


private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}


private fun WeekCalendarLayoutInfo.firstMostVisibleWeek(viewportPercent: Float = 50f): Week? {
    return if (visibleWeeksInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleWeeksInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.week
    }
}

fun CalendarDay.week(): Week{
    val weekList = mutableListOf<WeekDay>()
    val dayOrder = this.date.dayOfWeek.ordinal
    var mondayLocalDate = this.date.minusDays(dayOrder.toLong())

    DayOfWeek.values().forEach {
        weekList.add(WeekDay(mondayLocalDate, WeekDayPosition.RangeDate))
        mondayLocalDate = mondayLocalDate.plusDays(1)
    }
    return Week(weekList)

}

fun WeekDay.week(): Week{
    val weekList = mutableListOf<WeekDay>()
    val dayOrder = this.date.dayOfWeek.ordinal
    var mondayLocalDate = this.date.minusDays(dayOrder.toLong())

    DayOfWeek.values().forEach {
        weekList.add(WeekDay(mondayLocalDate, WeekDayPosition.RangeDate))
        mondayLocalDate = mondayLocalDate.plusDays(1)
    }
    return Week(weekList)

}

