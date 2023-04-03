package ru.megboyzz.dnevnik.screens.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.megboyzz.dnevnik.navigate
import ru.megboyzz.dnevnik.navigation.BaseNavRote
import ru.megboyzz.dnevnik.ui.theme.MainText
import ru.megboyzz.dnevnik.ui.theme.dark
import ru.megboyzz.dnevnik.ui.theme.mainBlue
import ru.megboyzz.dnevnik.ui.theme.white

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