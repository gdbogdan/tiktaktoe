package com.example.tiktaktoe_jet

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun AdaptiveBipanelScreen(navController: NavHostController, viewModelFactory: ViewModelProvider.Factory) {
    val sharedViewModel: SharedViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel(factory = viewModelFactory)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if (maxWidth < 600.dp) {
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
                    if(sharedViewModel.difficulty.value == "multijugador"){
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
    Game(
        navController = navController,
        sharedViewModel = sharedViewModel,
        gameViewModel = gameViewModel,
        modifier = modifier
    )
}
