package ru.megboyzz.dnevnik.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.screens.ui.UnderlinedText
import ru.megboyzz.dnevnik.screens.ui.main.SettingsScaffold
import ru.megboyzz.dnevnik.ui.theme.H2

@Composable
fun SettingsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    SettingsScaffold(
        onLeave = {
                  //navController.navigate()
        },
        scaffoldState = scaffoldState,
        navController = navController
    ) {
        var enabled by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ){

            SettingsCategory(title = R.string.settings_category_accont.AsString()) {

                SettingsBaseAccountHeader(
                    name = "Киц Михаил",
                    studentClass = "11-А"
                )

            }

            SettingsCategory(title = R.string.settings_category_application.AsString()) {

                SettingsMenuItem(
                    title = R.string.title_choose_icons.AsString(),
                    icon = R.drawable.ic_choose_icons.AsPainter(),
                    onClick = {}
                )

                SettingsMenuSwitch(
                    title = R.string.title_enable_notif.AsString(),
                    enabled = enabled,
                    onChange = {
                        enabled =! it
                    }
                )

            }

            Spacer(modifier = Modifier.weight(1f))

            SettingsCategory(title = R.string.settings_category_author.AsString()) {

                SettingsMenuItem(
                    title = R.string.settings_category_author.AsString(),
                    icon = R.drawable.ic_author.AsPainter()
                ) {

                }

            }

        }
    }

}


@Composable
fun SettingsCategory(
    title: String,
    content: @Composable () -> Unit
) {
    Column{
        UnderlinedText(title)
        SpacerHeight(height = 10.dp)
        Column { content.invoke() }
    }
}


@Composable
fun SettingsBaseMenuItem(
    title: String,
    onClick: () -> Unit,
    leadingContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.mainClickable { onClick.invoke() },
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        leadingContent.invoke()
        Text(
            text = title,
            style = H2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
fun SettingsMenuItem(
    title: String,
    icon: Painter,
    onClick: () -> Unit
) {

    SettingsBaseMenuItem(
        title = title,
        onClick = onClick
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = "",
            )
        }

    }

}

@Composable
fun SettingsMenuSwitch(
    title: String,
    enabled: Boolean,
    onChange: (Boolean) -> Unit
) {

    SettingsBaseMenuItem(
        title = title,
        onClick = { onChange.invoke(enabled) }
    ) {
        Switch(checked = enabled, onCheckedChange = { onChange.invoke(enabled) })
    }

}

@Composable
fun SettingsMenuCheckBox(
    title: String,
    enabled: Boolean,
    onChange: (Boolean) -> Unit
) {

    SettingsBaseMenuItem(
        title = title,
        onClick = { onChange.invoke(enabled) }
    ) {
        Checkbox(checked = enabled, onCheckedChange = { onChange.invoke(enabled) })
    }

}

@Composable
fun SettingsBaseAccountHeader(

    name: String,
    studentClass: String,

) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ){
            Image(
                painter = R.drawable.ic_author.AsPainter(),
                contentDescription = ""
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = name,
                style = H2
            )
            Text(
                text = studentClass,
                style = H2
            )
        }
    }

}