package ru.megboyzz.dnevnik.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import ru.megboyzz.dnevnik.R

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


val H1 = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold
)

val H2 = TextStyle(
    fontSize = 15.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = (12 * 1.315).sp
)

val MainText = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = (10 * 1.56).sp
)

val Big = TextStyle(
    fontSize = 27.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = (22 * 1.475).sp
)

val Mini = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Bold,
)