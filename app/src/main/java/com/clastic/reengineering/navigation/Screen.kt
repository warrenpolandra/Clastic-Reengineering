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
    data object Rewards: Screen("rewards")
    data object RewardDetail: Screen("rewardDetail/{rewardId}") {
        fun createRoute(rewardId: String) = "rewardDetail/$rewardId"
    }
    data object RewardCart: Screen("rewardCart")
    data object RewardTransactionDetail: Screen("rewardTransactionDetail/{rewardTransactionId}") {
        fun createRoute(rewardTransactionId: String) = "rewardTransactionDetail/$rewardTransactionId"
    }
    data object RewardTransactionHistory: Screen("rewardTransactionHistory")
    data object RewardInventory: Screen("rewardInventory")
    data object Mission: Screen("mission")
    data object MissionDetail: Screen("missionDetail/{missionId}") {
        fun createRoute(missionId: String) = "missionDetail/${missionId}"
    }
    data object MissionSubmitDetail: Screen("missionSubmitDetail/{missionId}") {
        fun createRoute(missionId: String) = "missionSubmitDetail/$missionId"
    }
    data object MissionTransactionDetail: Screen("missionTransactionDetail/{missionTransactionId}") {
        fun createRoute(missionTransactionId: String) = "missionTransactionDetail/$missionTransactionId"
    }
    data object MissionTransactionHistory: Screen("missionTransactionHistory")
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
    data object PlasticTransactionDetail: Screen("plasticTransactionDetail/{plasticTransactionId}") {
        fun createRoute(plasticTransactionId: String) = "plasticTransactionDetail/$plasticTransactionId"
    }
    data object PlasticKnowledge: Screen("plasticKnowledge/{plasticKnowledgeId}") {
        fun createRoute(plasticKnowledgeId: String) = "plasticKnowledge/$plasticKnowledgeId"
    }
    data object PlasticTransactionHistory: Screen("plasticTransactionHistory")
    data object Leaderboard: Screen("leaderboard")
}