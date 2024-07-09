package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.GameViewModel
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.SharedViewModel

@Composable
fun AdaptiveBipanelScreen(navController: NavHostController, viewModelFactory: ViewModelProvider.Factory, sharedViewModel: SharedViewModel) {
    val gameViewModel: GameViewModel = viewModel(factory = viewModelFactory)
    val configuration = LocalConfiguration.current
    val isPortrait = remember { configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
    val isLandscape = remember { configuration.orientation == Configuration.ORIENTATION_LANDSCAPE }


    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        println(maxWidth)
        println(maxHeight)
        if ((isPortrait && maxWidth < 600.dp) || (isLandscape && maxHeight < 600.dp)) {
            // Modo de pantalla única (para móviles)
            SinglePanelScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                gameViewModel = gameViewModel,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            if (maxHeight > maxWidth) {
                // Modo vertical (para tabletas)
                Column(modifier = Modifier.fillMaxSize()) {
                    if(sharedViewModel.difficulty.equals("multijugador")){
                        MultiGame(
                            navController = navController,
                            sharedViewModel = sharedViewModel,
                            gameViewModel = gameViewModel,
                            modifier = Modifier.weight(1f)
                        )
                    }else{
                        Game(
                            navController = navController,
                            sharedViewModel = sharedViewModel,
                            gameViewModel = gameViewModel,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Log(modifier = Modifier.weight(1f), viewModel = gameViewModel)
                }
            } else {
                // Modo horizontal (para tabletas)
                Row(modifier = Modifier.fillMaxSize()) {
                    Game(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        gameViewModel = gameViewModel,
                        modifier = Modifier.weight(1f)
                    )
                    Log(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SinglePanelScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    if(sharedViewModel.difficulty.equals("multijugador")){
        MultiGame(
            navController = navController,
            sharedViewModel = sharedViewModel,
            gameViewModel = gameViewModel,
            modifier = modifier
        )
    }else{
        Game(
            navController = navController,
            sharedViewModel = sharedViewModel,
            gameViewModel = gameViewModel,
            modifier = modifier
        )
    }
}
