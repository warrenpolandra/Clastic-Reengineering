package com.clastic.reengineering.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splashScreen")
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object Home: Screen("home")
    data object Article: Screen("article")
    data object ArticleDetail: Screen("articleDetail/{articleUrl}") {
        fun createRoute(articleUrl: String) = "articleDetail/$articleUrl"
    }
    data object Store: Screen("store")
    data object Mission: Screen("mission")
    data object MissionDetail: Screen("missionDetail/{missionId}") {
        fun createRoute(missionId: String) = "missionDetail/${missionId}"
    }
    data object Profile: Screen("profile")
    data object DropPointMap: Screen("dropPointMap")
    data object QrCode: Screen("qrCode/{userId}") {
        fun createRoute(userId: String) = "qrCode/$userId"
    }
    data object QrCodeScanner: Screen("qrCodeScanner")
    data object Tutorial: Screen("tutorial")

    data object PlasticTransaction: Screen("plasticTransaction/{userId}") {
        fun createRoute(userId: String) = "plasticTransaction/$userId"
    }
}