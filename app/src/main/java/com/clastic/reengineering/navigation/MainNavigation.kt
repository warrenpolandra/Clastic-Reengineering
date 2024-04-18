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
import com.clastic.mission.detail.MissionDetailScreen
import com.clastic.mission.list.ListMissionScreen
import com.clastic.qrcode.MyQrCodeScreen
import com.clastic.qrcode.scanner.QrScannerScreen
import com.clastic.splashscreen.ClasticSplashScreen
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
                ClasticSplashScreen(
                    navigateToLogin = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Login.route)
                    },
                    navigateToHome = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    navigateToRegister = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Register.route)
                    },
                    navigateToHome = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navigateToLogin = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Login.route)
                    },
                    navigateToHome = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(Screen.Home.route) {
                bottomBarVisible = true
                HomeScreen(
                    onPlasticTypeClicked = { plasticTag -> },
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
                DropPointMapScreen(navigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Home.route)
                })
            }
            composable(
                route = Screen.QrCode.route,
                arguments = listOf(navArgument("userId"){ type = NavType.StringType })
            ) { navBackStackEntry ->
                bottomBarVisible = false
                val userId = navBackStackEntry.arguments?.getString("userId")
                MyQrCodeScreen(
                    qrText = userId ?: "",
                    navigateToHome = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(Screen.QrCodeScanner.route) {
                bottomBarVisible = false
                QrScannerScreen(
                    onScannedSuccess = {
                        // TODO: navigate to Transaction
                    },
                    navigateToHome = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Home.route)
                    }
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
                    navigateToListArticle = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Article.route)
                    }
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
                    navigateToMissionList = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Mission.route)
                    }
                )
            }
        }
    }
}