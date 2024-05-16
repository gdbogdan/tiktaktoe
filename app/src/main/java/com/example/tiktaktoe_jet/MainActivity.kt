package com.example.tiktaktoe_jet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tiktaktoe_jet.ui.theme.TikTakToeTheme
import com.example.tiktaktoe_jet.ui.theme.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    private val viewModel = GameViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val window = rememberWindowSizeClass()
            TikTakToeTheme(window) {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "screen_menu"){
                    composable(
                        route = "screen_menu"
                    ){
                        Menu(navController)

                    }

                    composable(
                        route = "screen_help"
                    ){
                        Help( navController)
                    }

                    composable(
                        route = "screen_preparation"
                    ){
                        PreparationGame( navController)
                    }
                    composable(
                        route ="screen_game/{aliasText}/{timer}",
                        arguments = listOf(
                            navArgument("aliasText"){
                                type = NavType.StringType
                            },
                            navArgument("timer"){
                                type = NavType.BoolType
                            }
                        )
                    ){
                        backstackEntry ->
                        Game(
                            navController,
                            aliasText = backstackEntry.arguments?.getString("aliasText"),
                            time = backstackEntry.arguments?.getBoolean("timer"),
                            viewModel = viewModel
                            )

                    }

                    composable(
                        route ="screen_multigame/{aliasText}/{timer}",
                        arguments = listOf(
                            navArgument("aliasText"){
                                type = NavType.StringType
                            },
                            navArgument("timer"){
                                type = NavType.BoolType
                            }
                        )
                    ){
                            backstackEntry ->
                        MultiGame(
                            navController,
                            aliasText = backstackEntry.arguments?.getString("aliasText"),
                            time = backstackEntry.arguments?.getBoolean("timer"),
                            viewModel = viewModel
                        )

                    }
                    composable(
                        route ="screen_postgame/{aliasText}/{winner}/{totalTime}",
                        arguments = listOf(
                            navArgument("aliasText"){
                                type = NavType.StringType
                            },
                            navArgument("winner"){
                                type = NavType.StringType
                            },
                            navArgument("totalTime"){
                                type = NavType.StringType
                            }
                        )
                    ){
                        backstackEntry ->
                        PostGame(
                            navController,
                            aliasText = backstackEntry.arguments?.getString("aliasText"),
                            winner = backstackEntry.arguments?.getString("winner"),
                            totalTime = backstackEntry.arguments?.getString("totalTime")
                        )

                    }
                }

            }
        }
    }
}








