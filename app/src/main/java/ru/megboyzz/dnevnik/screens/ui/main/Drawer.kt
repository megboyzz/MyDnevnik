package ru.megboyzz.dnevnik.screens.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.*
import ru.megboyzz.dnevnik.R
import ru.megboyzz.dnevnik.navigation.AppNavRoute
import ru.megboyzz.dnevnik.screens.ui.AlmostOutlinedText
import ru.megboyzz.dnevnik.screens.ui.LeaveAlert
import ru.megboyzz.dnevnik.screens.ui.ProfileCard
import ru.megboyzz.dnevnik.ui.theme.H2
import ru.megboyzz.dnevnik.ui.theme.Shapes
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white
import ru.megboyzz.dnevnik.viewmodel.ProfileViewModel
import ru.megboyzz.dnevnik.viewmodel.ProfileViewModelFactory
import ru.megboyzz.dnevnik.viewmodel.model.UserProfileModel


@Composable
fun NiceContainerForDrawer(
    content: @Composable () -> Unit
) {

    val elevation = 16.dp

    val shape = RoundedCornerShape(
        topEnd = 20.dp,
        bottomEnd = 20.dp
    )

    Box(
        modifier = Modifier
            .shadow(elevation)
            .zIndex(1f)
            .clip(shape)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = mainBlue,
                    shape = shape
                )
                .clip(shape)
        ) {
            content.invoke()
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    val app = (LocalContext.current as MainActivity).app

    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(app)
    )

    val profile = profileViewModel.profile.collectAsState()

    val dao = app.database.credentialsDao()


    var leaveAlertIsOpened by remember {
        mutableStateOf(false)
    }

    var isLeavingProcess by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    if(leaveAlertIsOpened){
        LeaveAlert(
            isLoading = isLeavingProcess,
            onAgree = {
                scope.launch(Dispatchers.IO) {
                    isLeavingProcess = true
                    dao.deleteCredentials()
                    leaveAlertIsOpened = false
                    this.launch(Dispatchers.Main) { navController.navigate(AppNavRoute.Login) }
                    scaffoldState.drawerState.close()
                }
            },
            onCancel = { leaveAlertIsOpened = false }
        )
    }

    NiceContainerForDrawer {
        Column(
            modifier = Modifier
                .width(260.dp)
                .fillMaxSize()
                .padding(10.dp)
                .background(mainBlue),
            horizontalAlignment = Alignment.Start,
        ) {
            SpacerHeight(10.dp)
            if(profile.value == null)
                CircularProgressIndicator()
            else
                ProfileCard(userProfileModel = profile.value!!)
            SpacerHeight(15.dp)
            if(profile.value == null)
                CircularProgressIndicator()
            else
                AlmostOutlinedText(
                    text = if(profile.value!!.isTerm)
                        String.format(R.string.title_quatter_format.AsString(), profile.value!!.term)
                    else
                        String.format(R.string.title_sem_format.AsString(), profile.value!!.term)
                )
            SpacerHeight(20.dp)
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                DrawerMainButton(
                    icon = R.drawable.ic_marks.AsPainter(),
                    text = R.string.title_marks.AsString(),
                    onClick = {
                        navController.navigate(AppNavRoute.Marks)
                        scope.launch { scaffoldState.drawerState.close() }
                    }
                )
                DrawerMainButton(
                    icon = R.drawable.ic_homework.AsPainter(),
                    text = R.string.title_hw.AsString(),
                    onClick = {
                        navController.navigate(AppNavRoute.HomeWorks)
                        scope.launch { scaffoldState.drawerState.close() }
                    }
                )
                DrawerMainButton(
                    icon = R.drawable.ic_schedule.AsPainter(),
                    text = R.string.title_schedule.AsString(),
                    onClick = {
                        navController.navigate(AppNavRoute.Schedule)
                        scope.launch { scaffoldState.drawerState.close() }
                    }
                )
                DrawerMainButton(
                    icon = R.drawable.ic_settings.AsPainter(),
                    text = R.string.title_settings.AsString(),
                    onClick = {
                        navController.navigate(AppNavRoute.Settings)
                        scope.launch { scaffoldState.drawerState.close() }
                    }
                )
            }
            Spacer(Modifier.weight(1f))
            Divider(color = white, thickness = 1.dp)
            SpacerHeight(5.dp)
            Column(Modifier.padding(5.dp, 0.dp)) {
                DrawerMainButton(
                    icon = R.drawable.ic_exit.AsPainter(),
                    text = R.string.title_leave_from_acconut.AsString()
                ) {
                    leaveAlertIsOpened = true
                }
            }
        }
    }
}

@Composable
fun DrawerMainButton(
    icon: Painter,
    text: String,
    textColor: Color = white,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .mainClickable { onClick.invoke() }
    ){
        Image(
            painter = icon,
            contentDescription = "icon",
            Modifier.padding(start = 10.dp)
        )
        SpacerWidth(13.dp)
        Text(
            text = text,
            style = H2,
            color = textColor
        )
    }
}