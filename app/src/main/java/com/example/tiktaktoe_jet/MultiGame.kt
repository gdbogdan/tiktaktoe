package com.example.tiktaktoe_jet

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simonsays_jet.R
@Composable
fun MultiGame(
    navController: NavHostController, aliasText: String?, time: Boolean?, viewModel: GameViewModel = GameViewModel()
){
    val seconds by viewModel.seconds.collectAsState()
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
                    Text(text = "$seconds", fontSize = 25.sp)
                }
            }
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val winnerMessage: String = if(viewModel.winner == "isTie"){
                    "Empate"
                }else
                    "${viewModel.winner} Ganó"
                if (viewModel.winner != null){
                    Text(
                        text =  winnerMessage,
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Board(
                    boardState = viewModel.boardState,
                    onCellClick = { row, col ->
                        viewModel.onCellClick(row, col)
                    }
                )
                val totalTime: String = if(time == false){
                   "0"
               }else{
                   seconds.toString()
               }
                Spacer(modifier = Modifier.height(16.dp))
                if(viewModel.winner != null) {
                    Button(
                        onClick = {
                            viewModel.resetBoard()
                            navController.navigate("screen_postgame/$aliasText/${viewModel.winner}/$totalTime")
                        }) {
                        Text(text = stringResource(id = R.string.End))
                    }
                }
            }
        }
    }
}





