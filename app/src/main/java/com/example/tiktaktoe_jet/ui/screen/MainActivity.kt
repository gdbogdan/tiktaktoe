package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.GameViewModel
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.factory.GameViewModelFactory
import com.example.tiktaktoe_jet.Resultados_Partidas
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository.PreferencesRepository
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository.PreparationRepository
import com.example.tiktaktoe_jet.ui.theme.TikTakToeTheme
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.utils.rememberWindowSizeClass
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.SharedViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencesRepository = PreferencesRepository(applicationContext)
        val gameViewModelFactory = GameViewModelFactory(preferencesRepository)
        val preparationRepository = PreparationRepository(applicationContext)
        val sharedViewModel = SharedViewModel()

        preferencesRepository.clear()

        setContent {
            val window = rememberWindowSizeClass()
            TikTakToeTheme(window) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "screen_menu") {
                    composable(route = "screen_menu") {
                        Menu(navController)
                    }

                    composable(route = "screen_help") {
                        Help(navController)
                    }

                    composable(route = "screen_all_results") {
                        Resultados_Partidas(navController)
                    }
                    composable(route = "screen_log") {
                        val gameViewModel: GameViewModel = viewModel()
                        Log(viewModel = gameViewModel)
                    }

                    composable(route = "screen_preparation") {
                        PreparationGame(preparationRepository)
                    }

                    composable(route = "screen_screenPanel") {
                        AdaptiveBipanelScreen(navController, gameViewModelFactory, sharedViewModel)
                    }

                    composable(
                        route = "screen_postgame/{winner}/{totalTime}",
                        arguments = listOf(

                            navArgument("winner") { type = NavType.StringType },
                            navArgument("totalTime") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->

                        val winner = backStackEntry.arguments?.getString("winner")
                        val totalTime = backStackEntry.arguments?.getString("totalTime")
                        PostGame(navController, winner, totalTime)
                    }
                }
            }
        }
    }
}



