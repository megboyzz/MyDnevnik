package ru.megboyzz.dnevnik.screens.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.AsPainter
import ru.megboyzz.dnevnik.AsString
import ru.megboyzz.dnevnik.SpacerWidth
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.viewmodel.model.UserProfileModel

@Composable
fun MainScaffold(
    icon: Painter,
    title: String,
    navController: NavController,
    scaffoldState: ScaffoldState,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scope = rememberCoroutineScope()

    BaseScaffold(
        leadingIcon = Icons.Filled.Menu,
        trailingIcon = icon,
        onLeadingIconClick = {
            val drawerState = scaffoldState.drawerState
            scope.launch {
                if (drawerState.isClosed)
                    drawerState.open()
                else
                    drawerState.close()
            }
        },
        title = title,
        navController = navController,
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(navController) },
        drawerContent = {
            DrawerContent(navController, scaffoldState)
        },
        content = content
    )
}

@Composable
fun BaseScaffold(
    leadingIcon: ImageVector,
    trailingIcon: Painter?,
    onLeadingIconClick: () -> Unit,
    title: String,
    navController: NavController,
    scaffoldState: ScaffoldState,
    bottomBar: @Composable () -> Unit,
    drawerContent: @Composable (ColumnScope) -> Unit = {},
    content: (@Composable (it: PaddingValues) -> Unit),
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = mainBlue,
                contentColor = Color.White
            ) {
                IconButton(onClick = onLeadingIconClick) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = "",
                        tint = white
                    )
                }
                SpacerWidth(width = 5.dp)
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = white
                )
                if(trailingIcon != null) {
                    Spacer(Modifier.weight(1f))
                    Box(Modifier.padding(15.dp, 15.dp)) {
                        Image(painter = trailingIcon, contentDescription = "icon")
                    }
                }
            }
        },
        content = content,
        drawerBackgroundColor = Color.Transparent,
        drawerContentColor = Color.Transparent,
        //drawerShape = drawerShape,
        drawerElevation = 0.dp,
        drawerContent = drawerContent,
        bottomBar = bottomBar
    )
}

@Composable
fun SettingsScaffold(
    onLeave: () -> Unit,
    scaffoldState: ScaffoldState,
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    BaseScaffold(
        leadingIcon = Icons.Filled.ArrowBack,
        trailingIcon = R.drawable.ic_settings.AsPainter(),
        onLeadingIconClick = onLeave,
        title = R.string.title_settings.AsString(),
        navController = navController,
        scaffoldState = scaffoldState,
        bottomBar = {},
        content = content
    )
}