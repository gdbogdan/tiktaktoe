package com.example.tiktaktoe_jet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
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
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostGame(navController: NavHostController, aliasText: String?, winner: String?, totalTime: String?){
    val dateAndTime = Calendar.getInstance().time
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.purple_200)))
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.juego), modifier = Modifier.size(90.dp), contentDescription = null)
            Text(
                text = stringResource(id = R.string.Res),
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
            Text(text = stringResource(id = R.string.Data), fontWeight = FontWeight.Bold)
            var dataText by rememberSaveable {
                mutableStateOf("$dateAndTime")
            }
            TextField(
                value = dataText,
                onValueChange = { dataText = it },
                readOnly = true,
                modifier = Modifier.padding(top = 10.dp),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Timer, contentDescription = null
                    )
                }
            )

            Text(text = stringResource(id = R.string.Log), fontWeight = FontWeight.Bold)
            val winnerPlayer: String = if(winner == "X") {
                "Han ganado las X"
            }else{
                "Han ganado las O"
            }

            var logText by rememberSaveable {
                mutableStateOf("Alias: $aliasText, tamaño de la parrila: 3x3,\n resultado: $winnerPlayer,\n tiempo total: $totalTime")
            }
            TextField(
                value = logText,
                onValueChange = { logText = it },
                readOnly = true,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(text = stringResource(id = R.string.email), fontWeight = FontWeight.Bold)
            var emailText by rememberSaveable {
                mutableStateOf("")
            }
            TextField(
                value = emailText,
                onValueChange = { emailText = it },
                modifier = Modifier.padding(top = 10.dp),
                label = {
                    Text(text = stringResource(id = R.string.HintMail))
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mail, contentDescription = null
                    )
                }
            )
            val subject = "Partida tres en raya"
            val context = LocalContext.current
            Button(

                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, emailText)
                        putExtra(Intent.EXTRA_SUBJECT, subject)
                        putExtra(Intent.EXTRA_TEXT, logText)
                    }
                    context.startActivity(intent)
                }

            ) {
                Text(text = stringResource(id = R.string.btnEmail))
            }
            Button(
                onClick = { navController.navigate("screen_preparation") }

            ) {
                Text(text = stringResource(id = R.string.NewGame))
            }
            val activity = (LocalContext.current as? Activity)
            Button(
                onClick = { activity?.finish() }

            ) {
                Text(text = stringResource(id = R.string.Salir))
            }


        }
    }

}


