package com.example.tiktaktoe_jet

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R
import com.example.tiktaktoe_jet.ui.theme.AppTheme

var timer: Boolean = false
private var difficulty: String = ""

@Composable
fun PreparationGame(navController: NavHostController, viewModel: GameViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
    ) {
        Column(
            modifier = Modifier.padding(top = 10.dp).verticalScroll(scrollState)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.ajuste), contentDescription = null)
                Text(
                    text = stringResource(id = R.string.Conf),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),


                ) {
                Text(text = stringResource(id = R.string.Alias), fontWeight = FontWeight.Bold)
                var aliasText by rememberSaveable {
                    mutableStateOf("")
                }

                TextField(
                    value = aliasText,
                    modifier = Modifier.padding(top = 10.dp),
                    onValueChange = { aliasText = it },
                    label = {
                        Text(text = stringResource(id = R.string.HintAlias))
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person, contentDescription = null
                        )
                    },
                    supportingText = {
                        if (aliasText.isBlank()) {
                            Text(
                                text = stringResource(id = R.string.AliasError),
                                color = colorResource(id = R.color.red)
                            )
                        }
                    }


                )
                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.graella),
                        contentDescription = null,
                        Modifier.size(25.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.Dificultad),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                RadioButtonFun()

                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.reloj),
                        contentDescription = null,
                        Modifier.size(25.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.Tiempo),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(end = 20.dp)
                    )
                    SwitchFun()

                }
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (aliasText.isNotBlank()) {
                                viewModel.setDifficulty(difficulty)
                                if (difficulty == "individual")
                                    navController.navigate("screen_game/$aliasText/$timer")
                                if (difficulty == "multijugador")
                                    navController.navigate("screen_multigame/$aliasText/$timer")
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.Start))
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {navController.navigate("screen_menu") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.flecha_izquierda),
                            contentDescription = null
                        )
                        Text(text = stringResource(id = R.string.Menu))
                    }
                }
            }
        }


    }
}
@Composable
fun RadioButtonFun(){
    val options = listOf("individual", "multijugador")
    var selectedOption by rememberSaveable {
        mutableStateOf((options[0]))
    }
    Row{
        options.forEach{option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { selectedOption = option}
                )
                Text(text = option)
            }
        }

    }
    difficulty = selectedOption
}

@Composable
fun SwitchFun(){
    var isChecked by rememberSaveable {
        mutableStateOf(false)
    }
    Switch(
        checked = isChecked,
        onCheckedChange = { isChecked = it},
        thumbContent = {
            Icon(
                imageVector = if (isChecked) {
                    Icons.Default.Check
                }else {
                    Icons.Default.Close
                },
                contentDescription = null
            )
        }
    )
    timer = isChecked
}
