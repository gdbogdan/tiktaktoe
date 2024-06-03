package com.example.tiktaktoe_jet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tiktaktoe_jet.ui.theme.TikTakToeTheme
import com.example.tiktaktoe_jet.ui.theme.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val window = rememberWindowSizeClass()
            TikTakToeTheme(window) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "screen_menu") {
                    composable(
                        route = "screen_menu"
                    ){
                        Menu(navController)
                    }

                    composable(
                        route = "screen_help"
                    ){
                        Help(navController)
                    }
                    composable(
                        route = "screen_all_results"
                    ){
                        Resultados_Partidas(navController)
                    }

                    composable(
                        route = "screen_preparation"
                    ){
                        PreparationGame(navController)
                    }

                    composable(
                        route = "screen_game/{aliasText}/{timer}",
                        arguments = listOf(
                            navArgument("aliasText"){
                                type = NavType.StringType },
                            navArgument("timer"){
                                type = NavType.BoolType
                            }
                        )
                    ) {
                        backStackEntry ->
                        val aliasText = backStackEntry.arguments?.getString("aliasText")
                        val timer = backStackEntry.arguments?.getBoolean("timer")
                        val viewModelFactory = remember { GameViewModelFactory(SavedStateHandle()) }
                        Game(navController, aliasText, timer, viewModelFactory)
                    }

                    composable(
                        route = "screen_multigame/{aliasText}/{timer}",
                        arguments = listOf(
                            navArgument("aliasText"){
                                type = NavType.StringType },
                            navArgument("timer"){
                                type = NavType.BoolType }
                        )
                    ) { backStackEntry ->
                        val aliasText = backStackEntry.arguments?.getString("aliasText")
                        val timer = backStackEntry.arguments?.getBoolean("timer")
                        val viewModelFactory = remember { GameViewModelFactory(SavedStateHandle()) }
                        MultiGame(navController, aliasText, timer, viewModelFactory)
                    }

                    composable(
                        route = "screen_postgame/{aliasText}/{winner}/{totalTime}",
                        arguments = listOf(
                            navArgument("aliasText"){
                                type = NavType.StringType },
                            navArgument("winner"){
                                type = NavType.StringType },
                            navArgument("totalTime"){
                                type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val aliasText = backStackEntry.arguments?.getString("aliasText")
                        val winner = backStackEntry.arguments?.getString("winner")
                        val totalTime = backStackEntry.arguments?.getString("totalTime")
                        PostGame(navController, aliasText, winner, totalTime)
                    }
                }
            }
        }
    }
}
