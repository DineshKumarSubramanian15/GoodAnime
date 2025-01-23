package com.android.goodanime.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.goodanime.animeScreen.detailsScreen.GADetailsScreen
import com.android.goodanime.animeScreen.homeScreen.GAHomeScreen
import com.android.goodanime.ui.theme.GoodAnimeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoodAnimeTheme {
                val navController = rememberNavController()
                MainNavHost(navController)
            }
        }
    }

    @Composable
    private fun MainNavHost(
        navController: NavHostController,
    ) {
        NavHost(
            navController = navController,
            startDestination = GAMainDestinations.HomeScreen.route,
            builder = {
                composable(
                    route = GAMainDestinations.HomeScreen.route,
                    content = { GAHomeScreen(navController = navController) },
                )
                composable(
                    route = GAMainDestinations.DetailsScreen.route.plus("/{${GANavigationArguments.AnimeId}}"),
                    content = { GADetailsScreen(navController = navController) },
                    arguments = listOf(
                        navArgument(GANavigationArguments.AnimeId) {
                            type = NavType.IntType
                            nullable = false
                        }
                    )
                )
            }
        )
    }
}
