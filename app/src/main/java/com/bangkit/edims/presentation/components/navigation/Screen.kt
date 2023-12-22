package com.bangkit.edims.presentation.components.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Add : Screen("add")
    object Settings : Screen("settings")
    object Detail : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }

    object Profile : Screen("profile")
}