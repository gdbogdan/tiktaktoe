package com.example.tiktaktoe_jet

import android.widget.Chronometer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R

@Composable
fun Game(
    navController: NavHostController, aliasText: String?, difficulty: String?, time: Boolean?, viewModel: GameViewModel = GameViewModel()
){
    var chronometer: Chronometer?= null
    chronometer?.start()
    val state = viewModel.state.value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.purple_200)))
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp)
        ){
            Image(painter = painterResource(id = R.drawable.juego),modifier = Modifier.size(90.dp), contentDescription = null)
            Text(text = stringResource(id = R.string.Game), fontSize = 24.sp, modifier = Modifier.padding(start = 10.dp), fontWeight = FontWeight.Bold)

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .padding(start = 10.dp)
        ){
            if(time == true) {
                Text(text = stringResource(id = R.string.Timer), fontWeight = FontWeight.Bold, fontSize = 25.sp)
                /*TIMEEER*/

            }
        }
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val turn = if(state.isXTurn) "X" else "O"
            val turnMessage = "Turno de $turn"
            val winner = state.victory
            val winnerMessage = "$winner Ganó"
            val totalTime = "0"
            Text(
                text = if (winner !== null) winnerMessage else turnMessage,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.padding(10.dp)
            )
            BuildRow(rowId = 1, viewModel = viewModel)
            BuildRow(rowId = 2, viewModel = viewModel)
            BuildRow(rowId = 3, viewModel = viewModel)

            if(winner != null) {
                Button(
                    onClick = {
                        viewModel.resetBoard()
                        navController.navigate("screen_postgame/$aliasText/$winner/$totalTime")
                    }) {
                    Text(text = stringResource(id = R.string.End))
                }
            }
        }


    }
}

@Composable
fun BuildRow(
    rowId: Int,
    viewModel: GameViewModel
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        val third = (rowId * 3) -1
        val second = third -1
        val first = second -1
        val buttonColors = viewModel.state.value.buttonWinners
        val buttonsValues = viewModel.state.value.buttonValues
        TreesEnRayaButton(buttonsValues[first], buttonColors[first]) { viewModel.setButton(first)}
        TreesEnRayaButton(buttonsValues[second], buttonColors[second]) { viewModel.setButton(second)}
        TreesEnRayaButton(buttonsValues[third], buttonColors[third]) { viewModel.setButton(third)}
    }
}


@Composable
fun TreesEnRayaButton(
    button: String,
    changeColor: Boolean,
    onClick: () -> Unit
){
    val color = if(changeColor) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
    Button(
        onClick = onClick ,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = color
        )

    ) {
        Text(text = button, fontSize = 50.sp)
    }
}


