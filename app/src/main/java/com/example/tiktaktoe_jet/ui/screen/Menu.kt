package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Menu(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
    ) {
            Column(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .verticalScroll(rememberScrollState())) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)

            ){
                Image(
                    painter = painterResource(id = R.drawable.tiktak),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null)
                Text(
                    text = stringResource(id = R.string.TikTakToe),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp).padding(end = 80.dp),
                    fontWeight = FontWeight.Bold)
                Image(
                    painter = painterResource(id = R.drawable.ajuste),
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { navController.navigate("screen_preparation") },
                    contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            navController.navigate("screen_help")
                        },
                        modifier = Modifier.padding(bottom = 5.dp)
                    ) {
                        Text(text = stringResource(id = R.string.Ayuda))
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.navigate("screen_screenPanel") },
                        modifier = Modifier.padding(bottom = 5.dp)
                    ) {
                        Text(text = stringResource(id = R.string.Start))
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.navigate("screen_all_results") },
                        modifier = Modifier.padding(bottom = 5.dp)
                    ) {
                        Text(text = stringResource(id = R.string.Consultar_partidas))
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val activity = (LocalContext.current as? Activity)
                    Button(
                        onClick = { activity?.finish() }
                    ) {
                        Text(text = stringResource(id = R.string.Salir))
                    }
                }
            }

            }
    }
}


