package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.navigation.BaseNavRote
import ru.megboyzz.dnevnik.ui.theme.*
import java.time.DayOfWeek
import java.util.*


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

    // ?????????????? ?????????????? ?????? ?????????????????? ?? ?????????????? ?????????????? TODO - ????????????
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
    //???????????????? ?????????? ???????????? ???????????????? ??????????
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
            painter = painter, //TODO ???????????????? ?????????????????????? ??????????????????
            contentDescription = "chel"
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "?????? ?? ??????????????",
                style = H1,
                color = textColor
            )
            Text(
                text = "??????????/????????????",
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
        label = {
            Text(
                text = label,
                style = H2,
                color = dark
            )
        },
        shape = RoundedCornerShape(10.dp),
        textStyle = H2,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = dark,
            unfocusedBorderColor = if (isError.value) falseColor else mainBlue,
            focusedBorderColor = if (isError.value) falseColor else mainBlue,
            focusedLabelColor = dark,
            unfocusedLabelColor = dark,
            cursorColor = dark
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        visualTransformation =
        if (keyboardType == KeyboardType.Password) {
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, "")
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

//?????????????? ???????????????? ?????? ????????????, ???????????????? ?? ??????????????????????
@Composable
fun BaseCard(
    minWidth: Dp = 300.dp,
    //???????? ?????? ??????????????????
    nextLayer: @Composable () -> Unit = {},
    content: @Composable () -> Unit
){
    val shape = RoundedCornerShape(10.dp)
    //???????????????????????? ???????? ???????????????????????? ???????? ????????????????????
    Box(contentAlignment = Alignment.TopEnd) {
        Box(
            Modifier
                .clip(shape)
                .background(
                    color = white,
                    shape = shape
                )
                .defaultMinSize(minWidth)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = mainBlue,
                    ),
                    shape = shape
                )
        ) { content() }
        nextLayer()
    }
}

@Composable
fun AverageText(
    title: String,
    mark: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = Mini,
            color = white
        )
        Text(
            text = mark,
            style = MainText,
            color = white
        )
    }
}

@Composable
fun CardWithAverage(
    averageMark: Float?, 
    resultMark: Float?,
    minWidth: Dp = 300.dp,
    content: @Composable () -> Unit
) {
    BaseCard(
        minWidth = minWidth,
        nextLayer = {
            Box(
                modifier = Modifier
                    .size(100.dp, 50.dp)
                    .background(color = mainBlue, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
            ){
                Box(
                    modifier = Modifier.size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AverageText(
                        title = R.string.title_average_mark.AsString(), 
                        mark = if(averageMark != null) "%.2f".format(averageMark).replace(",", ".") else "-"
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .fillMaxSize()
                    .background(color = totalMarkColor, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ){
                AverageText(
                    title = R.string.title_result_mark.AsString(),
                    mark = if(resultMark != null) resultMark.toString() else "-"
                )
            }
        }
    ) { content() }
}

@Composable
fun LastMarkCard(
    subjectName: String,
    mark: Int,
    date: String, /* ?????????? ?????????? ???????????????? ???? Date ?????? ?????????????? */
    teacher: String,
    reason: String = "null"
) {
    BaseCard {
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

enum class Month{
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December,
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewCardCalendar(
    month: Month,
    year: Int,
    onClick: (day: Int) -> Unit,
) {
    val listOfDays = listOf(
        R.string.title_mon.AsString(),
        R.string.title_tue.AsString(),
        R.string.title_wed.AsString(),
        R.string.title_thu.AsString(),
        R.string.title_fri.AsString(),
        R.string.title_sat.AsString(),
    )

    //TODO ?????????????? ???????????????????????? ??????????????????
    val expanded = remember {
        mutableStateOf(true)
    }

    Card(
        backgroundColor = white,
        modifier = Modifier.defaultMinSize(minWidth = 300.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ){
        Column(
            modifier = Modifier.padding(0.dp)
        ) {
            /*LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 5.dp)
                    .defaultMinSize(minWidth = 300.dp)
            ){
                items(listOfDays){
                    Text(
                        text = it,
                        style = MainText,
                        color = dark
                    )
                }
            }
            Box(
                Modifier
                    .defaultMinSize(minWidth = 320.dp)
                    .height(1.dp)
                    .background(Color.Black))*/
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 6),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
            ){
                items(listOfDays){
                    Text(
                        text = it,
                        style = MainText,
                        color = dark
                    )
                }
                items((1..30).toList()){
                    NumberOnCalendar(
                        isClicked = remember { mutableStateOf(false) },
                        number = it.toString()
                    ) {

                    }
                }
            }

            val offset = 8 - getMonthDaysBy(year, month, DayOfWeek.MONDAY)[0]

            val clicked = mutableListOf<MutableState<Boolean>>()
        }
    }
}

@Composable
fun CardCalendar(
    month: Month,
    year: Int,
    onClick: (day: Int) -> Unit,
) {
    val listOfDays = listOf(
        R.string.title_mon.AsString(),
        R.string.title_tue.AsString(),
        R.string.title_wed.AsString(),
        R.string.title_thu.AsString(),
        R.string.title_fri.AsString(),
        R.string.title_sat.AsString(),
    )

    //TODO ?????????????? ???????????????????????? ??????????????????
    val expanded = remember {
        mutableStateOf(true)
    }

    Card(
        backgroundColor = white,
        modifier = Modifier.defaultMinSize(minWidth = 200.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ){
            Column(
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

                val offset = 8 - getMonthDaysBy(year, month, DayOfWeek.MONDAY)[0]

                val clicked = mutableListOf<MutableState<Boolean>>()
                var lastClicked = 0
                var lastInd = 0

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp, 0.dp, 5.dp)
                ) {
                    if(expanded.value)
                    for(day in DayOfWeek.values()){
                        if(day != DayOfWeek.SUNDAY) Column {
                            val list = getMonthDaysBy(year, month, day)
                            var len = 0
                            if(day.ordinal + 1 <= offset && offset !in 6..7){
                                EmptyNumberOnCalendar()
                                len++
                            }
                            for(i in list.indices){
                                val data = list[i]
                                val isClicked = remember { mutableStateOf(false) }
                                clicked.add(isClicked)
                                NumberOnCalendar(
                                    isClicked = isClicked,
                                    number = data.toString()
                                ) {
                                    expanded.value != expanded.value
                                    lastClicked = data
                                    lastInd = i
                                    for(click in clicked)
                                        if(click != isClicked)
                                            click.value = false
                                    onClick.invoke(data)
                                }
                                len++
                            }
                            if(day.ordinal + 1 > offset && len < 5){
                                EmptyNumberOnCalendar()
                            }
                        }
                    }
                    else{

                    }
                }
            }
        }
    }
}

@Composable
fun EmptyNumberOnCalendar(){
    NumberOnCalendar(isClicked = remember {
        mutableStateOf(false)
    }, number = "") {}
}

@Composable
fun NumberOnCalendar(
    isClicked: MutableState<Boolean>,
    number: String,
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(radius = 15.dp, color = dark),
                onClick = {
                    if (number != "") {
                        isClicked.value = !isClicked.value
                        onClick.invoke()
                    }
                }
            )
            .then(isClickedModifier),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = number,
            style = H2,
            color = dark,
        )
    }
}


@Composable
fun MonthToggle(
    startMonth: Month,
    startYear: Int,
    onClick: (day: Int) -> Unit
) {

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

    val month = remember {
        mutableStateOf(startMonth)
    }
    val year = remember {
        mutableStateOf(startYear)
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    var defaultPadding = 35.dp

    val isNormal = screenWidth < 500
    if(!isNormal) defaultPadding *= 4
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    var ind = month.value.ordinal - 1
                    if(ind == -1){
                        ind = 11
                        year.value = year.value - 1
                    }
                    month.value = Month.values()[ind]
                }) {
                    Image(
                        painter = R.drawable.ic_month_swipe_left.AsPainter(),
                        contentDescription = "left",
                        alignment = Alignment.Center
                    )
                }
                Text(
                    text = "${listOfMonth[month.value.ordinal]} ${year.value}",
                    style = H1,
                    color = dark
                )
                IconButton(
                    onClick = {
                        var ind = month.value.ordinal + 1
                        if(ind == 12){
                            ind = 0
                            year.value = year.value + 1
                        }
                        month.value = Month.values()[ind]
                    }
                ) {
                    Image(
                        painter = R.drawable.ic_month_swipe_right.AsPainter(),
                        contentDescription = "right",
                        alignment = Alignment.Center
                    )
                }
            }
        }
        CardCalendar(
            month = month.value,
            year = year.value,
            onClick = onClick
        )
    }
}

@Composable
fun UnderlinedText(
    text: String
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = dark,
                textAlign = TextAlign.Center,
                style = H1,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        SpacerHeight(height = 2.dp)
        Divider(color = dark, thickness = 1.dp)
    }
}

@Composable
fun AllMarksCard(
    subjectName: String,
    teacher: String?,
    marks: List<Int>?,
    resultMark: Float?
) {
    CardWithAverage(
        averageMark = marks?.average()?.toFloat(),
        resultMark = resultMark
    ) {
        Box(Modifier.padding(15.dp, 15.dp)) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = R.drawable.ic_subject.AsPainter(),
                        contentDescription = ""
                    )
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = subjectName,
                            color = dark,
                            style = MainText,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = teacher ?: "-",
                            color = dark,
                            style = MainText,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Divider(
                    color = dark,
                    thickness = 1.dp,
                    modifier = Modifier.padding(
                        top = 15.dp,
                        bottom = 10.dp,
                    )
                )
                
                if(marks != null)
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(marks){
                            MarkInBox(mark = it)
                        }
                    }
                else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        EmptyMessage(message = R.string.title_no_marks.AsString())
                    }
                }
            }
        }
    }
}

@Composable
fun MarkInBox(mark: Int) {
    Box(
        modifier = Modifier
            .size(19.dp)
            .fillMaxSize()
            .background(
                color = mainBlue,
                shape = RoundedCornerShape(2.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = mark.toString(),
            color = white
        )
    }
}

@Composable
fun ContentMessage(
    message: String,
    isFine: Boolean = true
) {
    Column(
        modifier = Modifier
            .defaultMinSize(200.dp)
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = if(isFine) R.drawable.ic_smile.AsPainter() else R.drawable.ic_sad_smile.AsPainter(),
            contentDescription = ""
        )
        SpacerHeight(height = 10.dp)
        Text(
            text = message,
            style = MainText,
            color = dark
        )
    }
}


@Composable
fun EmptyMessage(message: String) = ContentMessage(message = message, isFine = false)

@Composable
fun FineMessage(message: String) = ContentMessage(message = message)

@Composable
fun MessageCard(
    message: String,
    content: @Composable () -> Unit
) {
    BaseCard {

    }
}