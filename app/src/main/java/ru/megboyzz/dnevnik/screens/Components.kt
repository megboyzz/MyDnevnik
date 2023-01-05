package ru.megboyzz.dnevnik.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.Visibility
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.ui.theme.*

@Composable
fun MainScaffold(
    icon: Painter,
    title: String,
    /* TODO viewModel */
    content: (@Composable (it: PaddingValues) -> Unit)
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = mainBlue,
                contentColor = Color.White
            ) {
                IconButton(
                    onClick = {
                        val drawerState = scaffoldState.drawerState
                        scope.launch {
                            if (drawerState.isClosed)
                                drawerState.open()
                            else
                                drawerState.close()
                        }
                    }
                ) {
                    Image(painter = icon, contentDescription = "icon")
                }
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = white
                )
            }
        },
        content = content,
        drawerBackgroundColor = mainBlue,
        drawerContentColor = white,
        drawerShape = drawerShape,
        drawerContent = { DrawerContent() }
    )
}



@Composable
fun DrawerContent() {
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
                text = R.string.title_marks.AsString()
            ) { /* TODO */ }
            DrawerMainButton(
                icon = R.drawable.ic_homework.AsPainter(),
                text = R.string.title_hw.AsString()
            ) { /* TODO */ }
            DrawerMainButton(
                icon = R.drawable.ic_schedule.AsPainter(),
                text = R.string.title_schedule.AsString()
            ) { /* TODO */ }
            DrawerMainButton(
                icon = R.drawable.ic_settings.AsPainter(),
                text = R.string.title_settings.AsString()
            ) { /* TODO */ }
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

        return Outline.Rounded(RoundRect(
            left = 0f,
            top = 0f,
            right = size.width * 26 / 36,
            bottom = size.height,
            topLeftCornerRadius = nullRadius,
            topRightCornerRadius = radius,
            bottomRightCornerRadius = radius,
            bottomLeftCornerRadius = nullRadius
        ))
    }

}


@Composable
fun AlmostOutlinedText(
    text: String
){
    Column() {
        Divider(color = white, thickness = 1.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = white,
                textAlign = TextAlign.Center,
                style = H2,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Divider(color = white, thickness = 1.dp)
    }
}

@Composable
fun ProfileCard(
    textColor: Color = white,
    painter: Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier.padding(10.dp, 15.dp, 20.dp, 15.dp),
            painter = painter, //TODO заменить динмической картинкой
            contentDescription = "chel"
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "Имя и фамилия",
                style = H1,
                color = textColor
            )
            Text(
                text = "Класс/Группа",
                style = H1,
                color = textColor
            )
        }
    }
}

@Composable
fun LoginBackground() {
    val icon = R.drawable.ic_header.AsPainter()
    val size = icon.intrinsicSize
    Column {
        Image(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopStart
        )
    }

}



@Preview
@Composable
fun ProfilePrev() {
    ProfileCard(painter = R.drawable.ic_author.AsPainter())
}

@Composable
fun MainTextField(
    value: MutableState<String>,
    isError: MutableState<Boolean> = remember { mutableStateOf(false) },
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    onChange: (it: String) -> Unit = { value.value = it }
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    OutlinedTextField(
        value = value.value,
        onValueChange = onChange,
        label = { Text(
            text = label,
            style = H2,
            color = dark
        ) },
        shape = RoundedCornerShape(10.dp),
        textStyle = H2,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = dark,
            unfocusedBorderColor = if(isError.value) falseColor else mainBlue,
            focusedBorderColor = if(isError.value) falseColor else mainBlue,
            focusedLabelColor = dark,
            unfocusedLabelColor = dark,
            cursorColor = dark
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        visualTransformation =
        if(keyboardType == KeyboardType.Password) {
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        }else VisualTransformation.None,
        trailingIcon = {
            if(keyboardType == KeyboardType.Password){
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, "")
                }
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused)
                    if(isError.value) isError.value = false
            },
    )
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
            .clickable(onClick = onClick),
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

@Preview
@Composable
fun Almst() {
    Column() {
        AlmostOutlinedText(text = "Идет 3-я четверть")
    }
}

@Preview
@Composable
fun ButtonPrev() {
    DrawerMainButton(
        icon = R.drawable.ic_marks.AsPainter(),
        text = "Оценки"
    )
}


@Preview
@Composable
fun DrawerPrev() {
    Surface(
        color = mainBlue
    ) {
        DrawerContent()
    }
}

@Preview
@Composable
fun MainScaffoldPrev() {
    MainScaffold(
        icon = R.drawable.ic_marks.AsPainter(),
        title = R.string.title_marks.AsString()
    ) {

    }
}

@Preview
@Composable
fun CheckBoxed() {
    val isCheck = remember { mutableStateOf(false) }
    val titleColor = mainBlue
    Row {
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.5.dp, color = titleColor)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(if (isCheck.value) titleColor else Color.White)
                    .clickable {
                        isCheck.value = !isCheck.value
                    },
                contentAlignment = Alignment.Center
            ) {
                if(isCheck.value)
                    Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun PrevCB() {
    val checkedState = remember { mutableStateOf(true) }
    MainCheckbox(checkedState){

    }
}


@Composable
fun MainCheckbox(
    isChecked: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color = mainBlue,
    uncheckedColor: Color = white,
    checkmarkColor: Color = white,
    onValueChange: () -> Unit
) {

    val checkboxColor: Color by animateColorAsState(if (isChecked.value) checkedColor else uncheckedColor)
    val density = LocalDensity.current
    val duration = 200

    Row(
        modifier = modifier
            .toggleable(
                value = isChecked.value,
                role = Role.Checkbox,
                onValueChange = {
                    isChecked.value = !isChecked.value
                    onValueChange.invoke()
                }
            )
    ) {
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, color = checkedColor),
        ) {
            Box(
                modifier = Modifier
                    .size(size.dp)
                    .background(checkboxColor),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = isChecked.value,
                    enter = slideInHorizontally(
                        animationSpec = tween(duration)
                    ) {
                        (size * -0.5).toInt()
                    } + expandHorizontally(
                        expandFrom = Alignment.Start,
                        animationSpec = tween(duration)
                    ),
                    exit = fadeOut()
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = checkmarkColor
                    )
                }
            }
        }
    }
}

@Composable
fun MainButton(
    text: String,
    radius: Dp = 8.dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = mainBlue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(radius),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = H1
        )
    }
}