package ru.megboyzz.dnevnik.screens.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.ui.theme.MainText
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white
import ru.megboyzz.dnevnik.R

@Composable
fun BottomBar(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawColoredShadow(
                offsetY = 1.dp
            )
            .height(90.dp)
            .background(white)
            .padding(10.dp, 10.dp, 10.dp, 15.dp),
    ){
        Box(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(mainBlue)
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                BottomNavButton(
                    icon = R.drawable.ic_marks.AsPainter(),
                    text = R.string.title_marks.AsString(),
                    onClick = { navController.navigate(AppNavRoute.Marks) }
                )
                BottomNavButton(
                    icon = R.drawable.ic_homework.AsPainter(),
                    text = R.string.title_hw.AsString(),
                    onClick = { navController.navigate(AppNavRoute.HomeWorks) }
                )
                BottomNavButton(
                    icon = R.drawable.ic_schedule.AsPainter(),
                    text = R.string.title_schedule.AsString(),
                    onClick = { navController.navigate(AppNavRoute.Schedule) }
                )
            }
        }
    }
}

@Composable
fun BottomNavButton(
    icon: Painter,
    text: String,
    color: Color = white,
    onClick: () -> Unit,
) {
    //Возможно стоит убрать анимацию клика
    Box(
        Modifier
            .mainClickable(onClick = onClick, radius = 20.dp)
            .padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = icon,
                contentDescription = "",
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = text,
                style = MainText,
                color = color,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}