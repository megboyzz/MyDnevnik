package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.ui.theme.Big
import ru.megboyzz.dnevnik.ui.theme.MainText
import ru.megboyzz.dnevnik.ui.theme.Mini
import ru.megboyzz.dnevnik.ui.theme.dark
import ru.megboyzz.dnevnik.R

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
                    text =  R.string.title_welcome.AsString(),
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