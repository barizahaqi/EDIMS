package com.bangkit.edims.presentation.components.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Settings : Screen("settings")
    object Detail : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
    object Profile : Screen("profile")
    object HelpCenter : Screen("help_center")
}