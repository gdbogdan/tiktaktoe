package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simonsays_jet.R
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.SharedViewModel
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostGame(navController: NavHostController, winner: String?, totalTime: String?) {
    val dateAndTime = Calendar.getInstance().time
    val sharedViewModel: SharedViewModel = viewModel() // Using the shared ViewModel
    val aliasText by sharedViewModel.aliasText.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
    )
    Column(
        modifier = Modifier.padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top= 10.dp, bottom = 30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.juego),
                modifier = Modifier.size(90.dp),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.Res),
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 10.dp).padding(end = 80.dp),
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.ajuste),
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate("screen_preparation") },
                contentDescription = null
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
            val winnerPlayer: String = if (winner == "X") {
                stringResource(id = R.string.X)
            } else if (winner == "O") {
                stringResource(id = R.string.O)
            } else
                stringResource(id = R.string.Tie)

            var logText by rememberSaveable {
                mutableStateOf("Alias: $aliasText, tamaño de la parrila: 3x3,\n Resultado: $winnerPlayer,\n Tiempo total: $totalTime seg")
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
            val subject = stringResource(id = R.string.asunto)
            val context = LocalContext.current

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (isValidEmail(emailText)) {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailText)) // Array para destinatarios
                                putExtra(Intent.EXTRA_SUBJECT, subject)
                                putExtra(Intent.EXTRA_TEXT, logText)
                            }
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, context.getString(R.string.toastEmail), Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.btnEmail))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("screen_screenPanel") }
                ) {
                    Text(text = stringResource(id = R.string.NewGame))
                }
            }
            val activity = (LocalContext.current as? Activity)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { activity?.finish() }
                ) {
                    Text(text = stringResource(id = R.string.Salir))
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
