package com.example.tiktaktoe_jet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.simonsays_jet.R
import androidx.compose.runtime.*

@Composable
fun Game(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val time by sharedViewModel.time.observeAsState(false)
    val boardState by gameViewModel.boardState.collectAsState()
    val winner by gameViewModel.winner.collectAsState()
    val seconds by gameViewModel.seconds.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .background(colorResource(id = R.color.purple_200))
            .verticalScroll(rememberScrollState())
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
            println(time)
            if(time) {
                Text(text = stringResource(id = R.string.Timer), fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(text = "$seconds", fontSize = 25.sp)
            }
        }
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val winnerMessage: String = if(winner == "isTie"){
                "Empate"
            }else {
                "$winner Ganó"
            }
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
                    gameViewModel.onCellClick(row, col)
                })
            val totalTime: String = if(!time){
                "0"
            }else{
                seconds.toString()
            }
            Spacer(modifier = Modifier.height(16.dp))
            if(winner != null) {
                Button(
                    onClick = {
                        gameViewModel.resetBoard()
                        navController.navigate("screen_postgame/$winner/$totalTime")
                    }) {
                    Text(text = stringResource(id = R.string.End))
                }
            }
        }
    }
}


@Composable
fun Board(boardState: Array<Array<String>>, onCellClick: (Int, Int) -> Unit) {
    Column {
        for (row in boardState.indices) {
            Row {
                for (col in boardState[row].indices) {
                    Cell(boardState[row][col]) {
                        onCellClick(row, col)
                    }
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
            .padding(4.dp)
            .background(Color.Gray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = value, fontSize = 32.sp, color = Color.White)
    }
}
