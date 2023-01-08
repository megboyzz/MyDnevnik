package ru.megboyzz.dnevnik.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.Visibility
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.navigation.BaseNavRote
import ru.megboyzz.dnevnik.navigation.MarksNavRoute
import ru.megboyzz.dnevnik.ui.theme.*
import java.util.Calendar

@Composable
fun MainScaffold(
    icon: Painter,
    title: String,
    /* TODO viewModel */
    navController: NavController,
    scaffoldState: ScaffoldState,
    content: (@Composable (it: PaddingValues) -> Unit),
) {
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

                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "menu",
                        tint = white
                    )
                }
                SpacerWidth(width = 5.dp)
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = white
                )
                Spacer(Modifier.weight(1f))
                Box(Modifier.padding(15.dp, 15.dp)) {
                    Image(painter = icon, contentDescription = "icon")
                }
            }
        },
        content = content,
        drawerBackgroundColor = mainBlue,
        drawerContentColor = white,
        drawerShape = drawerShape,
        drawerContent = { DrawerContent(navController, scaffoldState) },
        bottomBar = { BottomBar(navController) }
    )
}

@Composable
fun BottomBar(navController: NavController) {
    //Box(Modifier.fillMaxWidth().height(90.dp).shadow(8.dp))

    // УЖАСНЫЙ КОСТЫЛЬ ДЛЯ ПЕРЕХОДОВ С ПОМОЩЬЮ СТЕЙТОВ TODO - УБРАТЬ
    val goToMarks = remember { mutableStateOf(false) }
    val goToHw = remember { mutableStateOf(false) }
    val goToSchedule = remember { mutableStateOf(false) }

    if(goToMarks.value){
        navController.navigate(AppNavRoute.Marks)
        //goToMarks.value = false
    }
    if(goToHw.value){
        navController.navigate(AppNavRoute.HomeWorks)
        //goToHw.value = false
    }
    if(goToSchedule.value){
        navController.navigate(AppNavRoute.Schedule)
        //goToSchedule.value = false
    }

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
                    text = R.string.title_marks.AsString()
                ) { goToMarks.value = true }
                BottomNavButton(
                    icon = R.drawable.ic_homework.AsPainter(),
                    text = R.string.title_hw.AsString()
                ) { goToHw.value = true }
                BottomNavButton(
                    icon = R.drawable.ic_schedule.AsPainter(),
                    text = R.string.title_schedule.AsString()
                ) { goToSchedule.value = true }
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
    Column(
        Modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = text,
            style = MainText,
            color = color
        )
    }
}



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DrawerContent(
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    // УЖАСНЫЙ КОСТЫЛЬ ДЛЯ ПЕРЕХОДОВ С ПОМОЩЬЮ СТЕЙТОВ TODO - УБРАТЬ
    val goToMarks = remember { mutableStateOf(false) }
    val goToHw = remember { mutableStateOf(false) }
    val goToSchedule = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    if(goToMarks.value){
        navController.navigate(AppNavRoute.Marks)
        scope.launch { scaffoldState.drawerState.close() }
    }
    if(goToHw.value){
        navController.navigate(AppNavRoute.HomeWorks)
        scope.launch { scaffoldState.drawerState.close() }
    }
    if(goToSchedule.value){
        navController.navigate(AppNavRoute.Schedule)
        scope.launch { scaffoldState.drawerState.close() }
    }

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
            ) { goToMarks.value = true  }
            DrawerMainButton(
                icon = R.drawable.ic_homework.AsPainter(),
                text = R.string.title_hw.AsString()
            ) { goToHw.value = true  }
            DrawerMainButton(
                icon = R.drawable.ic_schedule.AsPainter(),
                text = R.string.title_schedule.AsString()
            ) { goToSchedule.value = true }
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

@Composable
fun BigLoginBackground() {
    val icon = R.drawable.header_big.AsPainter()
    Column(Modifier.fillMaxWidth()) {
        Image(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.TopStart
        )
    }
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
                    if (isError.value) isError.value = false
            }
            .fillMaxWidth(),
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


@Composable
fun MainCheckbox(
    isChecked: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color = mainBlue,
    uncheckedColor: Color = lightGray,
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

@Composable
fun LoginScreenContent(
    login: MutableState<String>,
    password: MutableState<String>,
    isError: MutableState<Boolean>,
    isRememberMe: MutableState<Boolean>,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onForgetPassword: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    var defaultPadding = 65.dp

    val isNormal = screenWidth < 500

    Box(Modifier.fillMaxSize()) {

        if(!isNormal) BigLoginBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isNormal) LoginBackground()
            else {
                defaultPadding *= 4
            }
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = R.string.title_welcome.AsString(),
                    style = Big,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(defaultPadding, 20.dp, defaultPadding, 40.dp),
                    color = dark
                )
            }
            Column(
                modifier = Modifier.padding(defaultPadding, 0.dp)
            ) {
                Column(Modifier.fillMaxWidth()) {
                    MainTextField(
                        value = login,
                        label = R.string.title_login.AsString(),
                        isError = isError
                    )
                    SpacerHeight(15.dp)
                    MainTextField(
                        value = password,
                        keyboardType = KeyboardType.Password,
                        label = R.string.title_password.AsString(),
                        isError = isError
                    )
                }
                SpacerHeight(5.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MainCheckbox(isRememberMe) { }
                        SpacerWidth(5.dp)
                        Text(
                            text = R.string.title_remember_me.AsString(),
                            style = MainText,
                            color = dark
                        )
                    }
                    Text(
                        text = R.string.title_forget_password.AsString(),
                        style = MainText,
                        color = dark,
                        modifier = Modifier.clickable(onClick = onForgetPassword)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    MainButton(
                        text = R.string.title_sign_in.AsString(),
                        onClick = onSignIn
                    )
                    SpacerHeight(5.dp)
                    Text(
                        text = R.string.title_sign_up.AsString(),
                        modifier = Modifier
                            .clickable(onClick = onSignUp)
                            .padding(5.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = Mini,
                        color = dark
                    )
                }
            }
        }
    }
}

@Composable
fun SubScreenNavButton(
    text: String,
    isClicked: MutableState<Boolean>,
    onCLick: () -> Unit
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if(isClicked.value) mainBlue else white,
        contentColor =    if(isClicked.value) white else dark
     )
    Button(
        onClick = {
            isClicked.value = !isClicked.value
            onCLick.invoke()
        },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = mainBlue,
        ),
        colors = colors,
    ) {
        Text(
            text = text,
            style= MainText
        )
    }
}

data class SubScreenData(
    val title: String,
    val to: BaseNavRote,
    val isClicked: MutableState<Boolean>
)


@Composable
fun SubScreenNavBar(
    list: List<SubScreenData>,
    subScreenNavController: NavController
) {
    val horizontalScroll = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(horizontalScroll)
            .padding(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ){
        for(data in list){
            if(data.isClicked.value)
                subScreenNavController.navigate(data.to)
            SubScreenNavButton(
                text = data.title,
                isClicked = data.isClicked
            ) {
                for(d in list){
                    if(data != d)
                        d.isClicked.value = false
                }
            }
        }
    }
}


@Composable
fun LastMarkCard(
    subjectName: String,
    mark: Int,
    date: String, /* мейби стоит поменять на Date или инстанс */
    teacher: String,
    reason: String = "null"
) {
    Card(
        backgroundColor = white,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = mainBlue
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = subjectName,
                    style = MainText
                )
                Text(
                    text = mark.toString(),
                    style = H1,
                    color = mainBlue
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "${R.string.title_mark_recive.AsString()} $date",
                    style = MainText,
                    color = dark
                )
                Text(
                    text = "${R.string.title_teacher_name.AsString()}: $teacher",
                    style = MainText,
                    color = dark
                )
                if(reason != "null")
                    Text(
                        text = "${R.string.title_reason.AsString()} $reason",
                        style = MainText,
                        color = dark
                    )
            }
        }
    }
}

@Preview
@Composable
fun LastMarkPrev() {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        LastMarkCard(
            subjectName = "Русский язык",
            mark = 3,
            date = "03.02.2023",
            teacher = "Джаналиева Д. Ф.",
            reason = "диктнант"
        )
        LastMarkCard(
            subjectName = "Алгебра и начала математического анализа",
            mark = 5,
            date = "03.08.2023",
            teacher = "Штрошер С. Н.")
    }
}

data class CalendarData(
    val title: String,
    
)


@Composable
fun CardCalendar(
    onClick: (day: Int) -> Unit
) {
    //Calendar(Calendar.FEBRUARY)
    val listOfDays = listOf(
        R.string.title_mon.AsString(),
        R.string.title_tue.AsString(),
        R.string.title_wed.AsString(),
        R.string.title_thu.AsString(),
        R.string.title_fri.AsString(),
        R.string.title_sat.AsString(),
    )
    Card(
        backgroundColor = white,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ){
            Column(
                //horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 5.dp)
                ){
                    for(day in listOfDays){
                        Text(
                            text = day,
                            style = MainText,
                            color = dark
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = dark,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
                )
                val days = 28
                val weeks = days / 6
                var dayIn = 2
                val startDay = 1
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp, 10.dp, 5.dp)
                ) {
                    for (i in 1..6) {
                        //if(i != 7)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for(j in i..days step 7){
                                var text = j.toString()
                                if(j < startDay)
                                    text = ""
                                val isClicked = remember {
                                    mutableStateOf(false)
                                }
                                NumberOnCalendar(
                                    isClicked = isClicked,
                                    number = j
                                ) { onClick.invoke(j) }
                            }
                        }
                    }
                }

                /*for(day in 1..weeks){
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 10.dp, 10.dp, 5.dp)
                    ){
                        for(i in dayIn..dayIn + 5){
                            Text(
                                text = i.toString(),
                                color = dark,
                                style = MainText,
                                modifier = Modifier.clickable{
                                    onClick.invoke(i)
                                }
                            )
                        }
                        dayIn += 6
                    }
                }*/
            }
        }
    }
}

// TODO СДЕЛАТЬ КЛИКАБЕЛЬНОЕ ЧИСЛО НА КАЛЕНДАРЕ
@Composable
fun NumberOnCalendar(
    isClicked: MutableState<Boolean>,
    number: Int,
    onClick: () -> Unit
) {
    val isClickedModifier = if(isClicked.value)
    Modifier
        .border(
            width = 1.dp,
            color = mainBlue,
            shape = RoundedCornerShape(20.dp)
        )
        .background(
            color = lightGray,
            shape = RoundedCornerShape(20.dp)
        )
        .padding(2.dp)
    else Modifier
    Box(
        modifier = Modifier
            .size(30.dp)
            .then(isClickedModifier),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = number.toString(),
            style = H2,
            color = dark,
            modifier = Modifier.clickable{
                isClicked.value = !isClicked.value
                onClick.invoke()
            }
        )
    }
}

@Preview
@Composable
fun CalendarPrev() {
    val current = LocalContext.current
    CardCalendar(){
        Toast.makeText(current, "Число $it", Toast.LENGTH_SHORT).show()

    }
}