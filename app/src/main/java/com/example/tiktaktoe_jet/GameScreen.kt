package com.example.tiktaktoe_jet

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController

@Composable
fun GameScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
/*
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            BiPanelLayout(
                primaryContent = { Game() },
                secondaryContent = { Log() },
                isHorizontal = true
            )
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            BiPanelLayout(
                primaryContent = { Game(navController) },
                secondaryContent = { Log() },
                isHorizontal = false
            )
        }
        else -> {
            // Handle other cases if necessary
        }
    }
}

@Composable
fun BiPanelLayout(
    primaryContent: @Composable () -> Unit,
    secondaryContent: @Composable () -> Unit,
    isHorizontal: Boolean
) {
    if (isHorizontal) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.weight(1f).padding(8.dp)) {
                Game()
            }
            Box(modifier = Modifier.weight(1f).padding(8.dp)) {
                Log()
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.weight(1f).padding(8.dp)) {
                Game()
            }
            Box(modifier = Modifier.weight(1f).padding(8.dp)) {
                Log()
            }
        }
    }
}

@Composable
fun GameContent() {
    // Implement the game content here
    Text(text = "Game Content")
}

@Composable
fun LogContent() {
    // Implement the log content here
    Text(text = "Log Content")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameScreen()

 */
}
