package com.example.tiktaktoe_jet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R
import com.example.tiktaktoe_jet.ui.theme.AppTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Help(
    navController: NavHostController
) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.purple_200))
        )
    if (AppTheme.orientation == Orientation.Vertical) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp)

        ) {
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
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.Reglas),
                modifier = Modifier.padding(start = 10.dp)
            )
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
    }else{
        Column {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)

            ) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),

            ) {
                Text(
                    text = stringResource(id = R.string.Reglas),
                    modifier = Modifier
                        .padding(start = 10.dp)

                )
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
}
