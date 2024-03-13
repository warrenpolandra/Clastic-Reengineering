package com.clastic.reengineering.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splashScreen")
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object Home: Screen("home")
}