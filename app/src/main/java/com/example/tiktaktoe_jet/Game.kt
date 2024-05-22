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

var isTie = false
var winner: String? = null
var totalTime = 0

@Composable
fun Game(
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
                            currentPlayer = "X"
                            gameOver = isGameOver(boardState)
                            if (!gameOver) {
                                val computerMove = getComputerMove(boardState)
                                boardState = boardState.copyOf().apply {
                                    this[computerMove.first][computerMove.second] = "O"
                                }
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

@Composable
fun Board(boardState: Array<Array<String>>, onCellClick: (Int, Int) -> Unit) {
    Column {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    Cell(
                        value = boardState[row][col],
                        onClick = { onCellClick(row, col) }
                    )
                }
            }
        }
    }
}

@Composable
fun Cell(value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(colorResource(id = R.color.purple_500))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = value, fontSize = 32.sp)
    }
}

fun isGameOver(boardState: Array<Array<String>>): Boolean {
    // Check rows and columns for a winner
    for (i in 0..2) {
        if (boardState[i][0] != "" && boardState[i][0] == boardState[i][1] && boardState[i][1] == boardState[i][2]) {
            winner = boardState[i][0]
            return true
        }
        if (boardState[0][i] != "" && boardState[0][i] == boardState[1][i] && boardState[1][i] == boardState[2][i]) {
            winner = boardState[0][i]
            return true
        }
    }

    // Check diagonals for a winner
    if (boardState[0][0] != "" && boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]) {
        winner = boardState[0][0]
        return true
    }
    if (boardState[2][0] != "" && boardState[2][0] == boardState[1][1] && boardState[1][1] == boardState[0][2]) {
        winner = boardState[2][0]
        return true
    }

    // Check for a tie
    isTie = true
    for (row in boardState) {
        for (cell in row) {
            if (cell == "") {
                isTie = false
            }
        }
    }
    if (isTie)
        winner = "isTie"
    return isTie
}

fun getComputerMove(boardState: Array<Array<String>>): Pair<Int, Int> {
    // Get a list of empty cells
    val emptyCells = mutableListOf<Pair<Int, Int>>()
    for (i in 0..2) {
        for (j in 0..2) {
            if (boardState[i][j] == "") {
                emptyCells.add(Pair(i, j))
            }
        }
    }

    // If there are no empty cells, the game is over
    if (emptyCells.isEmpty()) {
        return Pair(-1, -1)
    }

    // Choose a random empty cell
    val randomIndex = Random.nextInt(emptyCells.size)
    return emptyCells[randomIndex]
}

