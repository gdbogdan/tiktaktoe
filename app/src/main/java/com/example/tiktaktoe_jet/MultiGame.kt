package com.example.tiktaktoe_jet

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.simonsays_jet.R
import kotlin.random.Random

@Composable
fun MultiGame(
    navController: NavHostController, aliasText: String?, time: Boolean?, viewModel: GameViewModel = GameViewModel()
){
    var boardState by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameOver by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.purple_200))
    ) {
        Column(
            modifier = Modifier.padding(top = 10.dp)
        ) {
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
            ) {
                val winnerMessage = "$winner Ganó"
                if (winner != null){
                    Text(
                        text =  winnerMessage,
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Board(
                    boardState = boardState,
                    onCellClick = { row, col ->
                        if (!gameOver && boardState[row][col] == "") {
                            boardState = boardState.copyOf().apply {
                                this[row][col] = currentPlayer
                            }
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                            gameOver = isGameOver(boardState)
                            if (!gameOver) {
                                gameOver = isGameOver(boardState)
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                if(winner != null) {
                    Button(
                        onClick = {
                            val winnerGame = winner
                            winner = null
                            viewModel.resetBoard()
                            navController.navigate("screen_postgame/$aliasText/$winnerGame/$totalTime")
                        }) {
                        Text(text = stringResource(id = R.string.End))
                    }
                }
            }
        }
    }
}





