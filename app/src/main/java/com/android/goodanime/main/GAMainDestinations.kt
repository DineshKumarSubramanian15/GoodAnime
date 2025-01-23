package com.android.goodanime.main

/**
 * The class that representing the destinations from the main navigation
 */
sealed class GAMainDestinations(val route: String) {
    object HomeScreen : GAMainDestinations(route = "HomeScreen")
    object DetailsScreen : GAMainDestinations(route = "DetailsScreen")
}