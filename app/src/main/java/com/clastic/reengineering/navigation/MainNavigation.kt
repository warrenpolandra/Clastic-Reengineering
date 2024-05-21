package com.clastic.reengineering.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.clastic.article.detail.ArticleDetailScreen
import com.clastic.article.list.ListArticleScreen
import com.clastic.authentication.login.LoginScreen
import com.clastic.authentication.register.RegisterScreen
import com.clastic.droppoint.DropPointMapScreen
import com.clastic.home.HomeScreen
import com.clastic.home.component.TutorialScreen
import com.clastic.leaderboard.LeaderboardScreen
import com.clastic.mission.detail.MissionDetailScreen
import com.clastic.mission.list.ListMissionScreen
import com.clastic.plastic_knowledge.PlasticKnowledgeScreen
import com.clastic.profile.ProfileScreen
import com.clastic.qrcode.MyQrCodeScreen
import com.clastic.qrcode.scanner.QrScannerScreen
import com.clastic.reward.RewardStoreScreen
import com.clastic.splashscreen.ClasticSplashScreen
import com.clastic.transaction.plastic.PlasticTransactionScreen
import com.clastic.transaction.plastic.detail.PlasticTransactionDetailScreen
import com.clastic.transaction.plastic.history.PlasticTransactionHistoryScreen
import java.net.URLDecoder

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    var bottomBarVisible by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (bottomBarVisible) BottomBar(navController = navHostController)
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.SplashScreen.route) {
                bottomBarVisible = false
                ClasticSplashScreen(
                    navigateToLogin = { navigateWithPopBack(navHostController, Screen.Login.route) },
                    navigateToHome = { navigateWithPopBack(navHostController, Screen.Home.route) }
                )
            }
            composable(Screen.Login.route) {
                bottomBarVisible = false
                LoginScreen(
                    navigateToRegister = { navigateWithPopBack(navHostController, Screen.Register.route) },
                    navigateToHome = { navigateWithPopBack(navHostController, Screen.Home.route) }
                )
            }
            composable(Screen.Register.route) {
                bottomBarVisible = false
                RegisterScreen(
                    navigateToLogin = { navigateWithPopBack(navHostController, Screen.Login.route) },
                    navigateToHome = { navigateWithPopBack(navHostController, Screen.Home.route) }
                )
            }
            composable(Screen.Home.route) {
                bottomBarVisible = true
                HomeScreen(
                    onPlasticTypeClicked = { plasticId ->
                        navHostController.navigate(Screen.PlasticKnowledge.createRoute(plasticId))
                    },
                    navigateToDropPointMap = { navHostController.navigate(Screen.DropPointMap.route) },
                    navigateToQrCode = { userId ->
                        navHostController.navigate(Screen.QrCode.createRoute(userId))
                    },
                    navigateToQrCodeScanner = { navHostController.navigate(Screen.QrCodeScanner.route) },
                    navigateToTutorial = { navHostController.navigate(Screen.Tutorial.route) },
                    onMissionClick = { missionTitle ->
                        navHostController.navigate(
                            Screen.MissionDetail.createRoute(missionTitle)
                        )
                    },
                )
            }
            composable(Screen.Tutorial.route) {
                bottomBarVisible = false
                TutorialScreen()
            }
            composable(Screen.DropPointMap.route) {
                bottomBarVisible = false
                DropPointMapScreen(navigateToHome = { navHostController.popBackStack() })
            }
            composable(
                route = Screen.QrCode.route,
                arguments = listOf(navArgument("userId"){ type = NavType.StringType })
            ) { navBackStackEntry ->
                bottomBarVisible = false
                val userId = navBackStackEntry.arguments?.getString("userId")
                MyQrCodeScreen(
                    qrText = userId ?: "",
                    navigateToHome = { navHostController.popBackStack() }
                )
            }
            composable(Screen.QrCodeScanner.route) {
                bottomBarVisible = false
                QrScannerScreen(
                    onScannedSuccess = { userId ->
                        navigateWithPopBack(navHostController, Screen.PlasticTransaction.createRoute(userId))
                    },
                    navigateToHome = { navHostController.popBackStack() }
                )
            }
            composable(
                route = Screen.PlasticTransaction.route,
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { navBackStackEntry ->
                val userId = navBackStackEntry.arguments?.getString("userId")
                PlasticTransactionScreen(
                    userId = userId ?: "",
                    navigateToHome = { navigateWithPopBack(navHostController, Screen.Home.route) },
                    navigateToPlasticTransactionDetail = { plasticTransactionId ->
                        navigateWithPopBack(navHostController, Screen.PlasticTransactionDetail.createRoute(plasticTransactionId))
                    }
                )
            }
            composable(
                route = Screen.PlasticTransactionDetail.route,
                arguments = listOf(navArgument("plasticTransactionId") { type = NavType.StringType })
            ) { navBackStackEntry ->
                bottomBarVisible = false
                val plasticTransactionId = navBackStackEntry.arguments?.getString("plasticTransactionId")
                PlasticTransactionDetailScreen(
                    plasticTransactionId = plasticTransactionId ?: "",
                    navigateToHome = { navigateWithPopBack(navHostController, Screen.Home.route) }
                )
            }
            composable(
                route = Screen.PlasticKnowledge.route,
                arguments = listOf(navArgument("plasticKnowledgeId") { type = NavType.StringType })
            ) { navBackStackEntry ->
                bottomBarVisible = false
                val plasticKnowledgeId = navBackStackEntry.arguments?.getString("plasticKnowledgeId")
                PlasticKnowledgeScreen(
                    plasticId = plasticKnowledgeId ?: "",
                    navigateToHome = { navHostController.popBackStack() }
                )
            }
            composable(Screen.Article.route) {
                bottomBarVisible = true
                ListArticleScreen(onArticleClick = { articleUrl ->
                    navHostController.navigate(Screen.ArticleDetail.createRoute(articleUrl))
                })
            }
            composable(
                route = Screen.ArticleDetail.route,
                arguments = listOf(navArgument("articleUrl"){ type = NavType.StringType })
            ) { navBackStackEntry ->
                bottomBarVisible = false
                val articleUrl = URLDecoder.decode(
                    navBackStackEntry.arguments?.getString("articleUrl"), "UTF-8"
                )
                ArticleDetailScreen(
                    contentUrl = articleUrl,
                    navigateToListArticle = { navHostController.popBackStack() }
                )
            }
            composable(Screen.Mission.route) {
                bottomBarVisible = true
                ListMissionScreen(onMissionClick = { missionId ->
                    navHostController.navigate(Screen.MissionDetail.createRoute(missionId))
                })
            }
            composable(
                route = Screen.MissionDetail.route,
                arguments = listOf(navArgument("missionId") { type = NavType.StringType})
            ) { navBackStackEntry ->
                bottomBarVisible = false
                MissionDetailScreen(
                    missionId = navBackStackEntry.arguments?.getString("missionId") ?: "",
                    navigateToMissionList = { navHostController.popBackStack() }
                )
            }
            composable(Screen.Profile.route) {
                bottomBarVisible = true
                ProfileScreen(
                    onLogout = { navigateWithPopBack(navHostController, Screen.Login.route) },
                    navigateToPlasticTransactionHistory = {
                        navHostController.navigate(Screen.PlasticTransactionHistory.route)
                    },
                    navigateToLeaderboard = {
                        navHostController.navigate(Screen.Leaderboard.route)
                    }
                )
            }
            composable(Screen.PlasticTransactionHistory.route) {
                bottomBarVisible = false
                PlasticTransactionHistoryScreen(
                    onTransactionClicked = { plasticTransactionId ->
                        navHostController.navigate(Screen.PlasticTransactionDetail.createRoute(plasticTransactionId))
                    },
                    navigateToProfile = { navHostController.popBackStack() }
                )
            }
            composable(Screen.Leaderboard.route) {
                bottomBarVisible = false
                LeaderboardScreen(navigateToProfile = { navHostController.popBackStack() })
            }
            composable(Screen.Rewards.route) {
                bottomBarVisible = true
                RewardStoreScreen(
                    onRewardClick = {/*TODO*/},
                    navigateToCart = {/*TODO*/}
                )
            }
        }
    }
}

private fun navigateWithPopBack(
    navHostController: NavHostController,
    screen: String
) {
    navHostController.apply {
        popBackStack()
        navigate(screen)
    }
}