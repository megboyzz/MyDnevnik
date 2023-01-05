package ru.megboyzz.dnevnik.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.SpacerHeight
import ru.megboyzz.dnevnik.SpacerWidth
import ru.megboyzz.dnevnik.ui.theme.*

@Composable
fun LoginScreen() {

    val login = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val defaultModifier = Modifier.width(500.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = defaultModifier
    ) {
        if(screenWidth < 500)
            LoginBackground()
        else{
            defaultModifier.then(Modifier.fillMaxWidth())
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = R.string.title_welcome.AsString(),
                style = Big,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(65.dp, 20.dp, 65.dp, 40.dp)
            )
        }
        MainTextField(
            value = login,
            label = R.string.title_login.AsString()
        )
        SpacerHeight(15.dp)
        MainTextField(
            value = password,
            keyboardType = KeyboardType.Password,
            label = R.string.title_password.AsString()
        )
        Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(65.dp, 10.dp)
               .height(30.dp),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val checkedState = remember { mutableStateOf(true) }
                MainCheckbox(checkedState) { /* TODO */ }
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
                modifier = Modifier.clickable{ /* TODO */ }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(65.dp, 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            MainButton(text = R.string.title_sign_in.AsString()) {
                /* TODO */
            }
            SpacerHeight(5.dp)
            Text(
                text = R.string.title_sign_up.AsString(),
                modifier = Modifier
                    .clickable {
                        /* TODO */
                    }
                    .padding(5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Mini
            )
        }

    }
}

@Preview
@Composable
fun LoginPagePrev() {
    LoginScreen()
}