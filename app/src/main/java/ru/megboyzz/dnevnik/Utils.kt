package ru.megboyzz.dnevnik


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import ru.megboyzz.dnevnik.navigation.BaseNavRote
import ru.megboyzz.dnevnik.screens.ui.Month
import ru.megboyzz.dnevnik.ui.theme.dark
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.temporal.TemporalAdjusters

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

@Composable
fun NavController.navigate(to: BaseNavRote){
    LaunchedEffect(Unit) {
        navigate(to.route) {
            popUpTo(to.route) { inclusive = true }
        }
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

fun getMonthDaysBy(year: Int, month: Month, dayOfWeek: DayOfWeek): List<Int>{
    var date: LocalDate =
        Year.of(year).atMonth(month.ordinal + 1).atDay(1).with(TemporalAdjusters.firstInMonth(dayOfWeek))
    var mi = date.month

    val list = mutableListOf<Int>()

    while (mi.ordinal == month.ordinal) {
        list.add(date.dayOfMonth)
        date = date.with(TemporalAdjusters.next(dayOfWeek))
        mi = date.month
    }
    return list
}

@Composable
fun Modifier.mainClickable(
    radius: Dp,
    onClick: () -> Unit
) = this.clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = rememberRipple(
        radius = radius,
        color = dark
    ),
    onClick = onClick
)
