package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.DialogProperties
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.ui.theme.*


//Рефакторинг компонентов: разбиение файла с компонентами на отдельные файлы
//Этот файл должен хранить только базовые реализации компонентов и общие компонеты компонеты



@Composable
fun AlmostOutlinedText(
    text: String,
    contentColor: Color = white,
    textAlign: TextAlign = TextAlign.Center
){
    Column() {
        Divider(color = contentColor, thickness = 1.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = contentColor,
                textAlign = textAlign,
                style = H2,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Divider(color = contentColor, thickness = 1.dp)
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

//Базовая карточка для оценок, карточек с диаграммами
@Composable
fun BaseCard(
    minWidth: Dp = 300.dp,
    //Слой над карточкой
    nextLayer: @Composable () -> Unit = {},
    content: @Composable () -> Unit
){
    val shape = RoundedCornerShape(10.dp)
    //Расположение слоя регулируется этим алигментом
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
    date: String, /* мейби стоит поменять на Date или инстанс */
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
fun MessageAlert(
    content: @Composable () -> Unit
) {
    BaseCard {
        Box(
            modifier = Modifier.defaultMinSize(300.dp),
            contentAlignment = Alignment.Center,
        ){
            content.invoke()
        }
    }
}

@Composable
fun MessageAlertInWork() {
    MessageAlert {
        EmptyMessage(message = "Раздел находится в разработке!")
    }
}


@Composable
fun BaseAlertBox(
    title: String?,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
    buttons: @Composable () -> Unit
) {
    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = onDismissRequest,
        title = {
                if(title != null)
                    Text(
                        text = title,
                        style = H1
                    )
        },
        text = {
            content.invoke()
        },
        buttons = {
            buttons.invoke()
        }
    )
}

@Composable
fun AlertMessageBox(
    title: String,
    alertText: String,
    onClick: () -> Unit,
    onCancel: () -> Unit = {},
) {

    BaseAlertBox(
        title = title,
        onDismissRequest = onCancel,
        content = {
            Text(
                text = alertText,
                style = H2,
                color = dark
            )
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = mainBlue,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .defaultMinSize(170.dp).padding(bottom = 30.dp)
                ) {
                    Text(
                        text = R.string.title_ok.AsString(),
                        style = H1
                    )
                }
            }
        }
    )

}
