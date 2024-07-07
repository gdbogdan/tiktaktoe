package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.ui.screen

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Help(navController: NavHostController) { //Necesitamos este controlador
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))

    ) {
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =Arrangement.Start ){

            Image(
                painter = painterResource(id = R.drawable.question_mark),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.Ayuda),
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Bold
            )
        }


        Row(modifier = Modifier.padding(start = 10.dp).padding(end = 10.dp).padding(bottom = 10.dp)) {
            Text(
                text = stringResource(id = R.string.Reglas),
                fontSize = 18.sp,
                textAlign = TextAlign.Justify
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("screen_menu") }) {
                Image(
                    painter = painterResource(id = R.drawable.flecha_izquierda),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.Menu),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}
