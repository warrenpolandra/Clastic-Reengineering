package com.clastic.reengineering.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splashScreen")
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object Home: Screen("home")
    data object Article: Screen("article")
    data object Store: Screen("store")
    data object Mission: Screen("mission")
    data object MissionDetail: Screen("missionDetail/{missionTitle}") {
        fun createRoute(missionTitle: String) = "missionDetail/${missionTitle}"
    }
    data object Profile: Screen("profile")
    data object DropPointMap: Screen("dropPointMap")
    data object QrCode: Screen("qrCode")
    data object QrCodeScanner: Screen("qrCodeScanner")
    data object Tutorial: Screen("tutorial")
}