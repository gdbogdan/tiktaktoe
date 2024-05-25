package com.example.tiktaktoe_jet

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Menu(navController: NavHostController
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.purple_200)))
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 10.dp)

    ){
        Image(painter = painterResource(id = R.drawable.tiktak), modifier = Modifier.size(150.dp), contentDescription = null)
        Text(text = stringResource(id = R.string.TikTakToe), fontSize = 24.sp, modifier = Modifier.padding(start = 10.dp), fontWeight = FontWeight.Bold)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Button(
            onClick = {
                navController.navigate("screen_help")
            },
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Text(text =  stringResource(id = R.string.Ayuda))
        }
        Button(
            onClick = { navController.navigate("screen_preparation") },
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Text(text =  stringResource(id = R.string.Partida))
        }
        val activity = (LocalContext.current as? Activity)
        Button(
            onClick = { activity?.finish() }

        ) {
            Text(text = stringResource(id = R.string.Salir))
        }
    }
}


