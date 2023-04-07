package ru.megboyzz.dnevnik.screens.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.white

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DrawerContent(
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .width(260.dp)
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start
    ){
        SpacerHeight(10.dp)
        ProfileCard(painter = R.drawable.ic_author.AsPainter())
        SpacerHeight(15.dp)
        AlmostOutlinedText(text = "Идет 3-я четверть")
        SpacerHeight(20.dp)
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(5.dp, 0.dp)
        ) {
            DrawerMainButton(
                icon = R.drawable.ic_marks.AsPainter(),
                text = R.string.title_marks.AsString(),
                onClick = {
                    navController.navigate(AppNavRoute.Marks)
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )
            DrawerMainButton(
                icon = R.drawable.ic_homework.AsPainter(),
                text = R.string.title_hw.AsString(),
                onClick = {
                    navController.navigate(AppNavRoute.HomeWorks)
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )
            DrawerMainButton(
                icon = R.drawable.ic_schedule.AsPainter(),
                text = R.string.title_schedule.AsString(),
                onClick = {
                    navController.navigate(AppNavRoute.Schedule)
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )
            DrawerMainButton(
                icon = R.drawable.ic_settings.AsPainter(),
                text = R.string.title_settings.AsString(),
                onClick = {
                    navController.navigate(AppNavRoute.Settings)
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )
        }
        Spacer(Modifier.weight(1f))
        Divider(color = white, thickness = 1.dp)
        SpacerHeight(5.dp)
        Column(Modifier.padding(5.dp, 0.dp)) {
            DrawerMainButton(
                icon = R.drawable.ic_exit.AsPainter(),
                text = R.string.title_leave_from_acconut.AsString()
            ) { /* TODO */ }
        }
    }
}

val drawerShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline.Rounded {

        val radius = CornerRadius(30f, 30f)
        val nullRadius = CornerRadius.Zero

        return Outline.Rounded(
            RoundRect(
            left = 0f,
            top = 0f,
            right = size.width * 26 / 36,
            bottom = size.height,
            topLeftCornerRadius = nullRadius,
            topRightCornerRadius = radius,
            bottomRightCornerRadius = radius,
            bottomLeftCornerRadius = nullRadius
        )
        )
    }

}

@Composable
fun DrawerMainButton(
    icon: Painter,
    text: String,
    textColor: Color = white,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .mainClickable(onClick = onClick),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ){
            Image(
                painter = icon,
                contentDescription = "icon"
            )
            SpacerWidth(13.dp)
            Text(
                text = text,
                style = H2,
                color = textColor
            )
        }
    }
}