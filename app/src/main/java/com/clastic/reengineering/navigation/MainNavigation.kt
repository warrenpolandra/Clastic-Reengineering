package com.clastic.reengineering.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clastic.authentication.login.LoginScreen
import com.clastic.authentication.register.RegisterScreen
import com.clastic.home.HomeScreen
import com.clastic.splashscreen.ClasticSplashScreen

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen.route
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
            HomeScreen(
                navigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Home.route)
                },
                // TODO: Complete Navigation
                navigateToArticle = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Article.route)
                },
                navigateToStore = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Store.route)
                },
                navigateToMission = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Mission.route)
                },
                navigateToProfile = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Profile.route)
                },
                onClick = { plasticTag -> },
                navigateToDropPointMap = { navHostController.navigate(Screen.DropPointMap.route) },
                navigateToQrCode = { navHostController.navigate(Screen.QrCode.route) },
                navigateToQrCodeScanner = { navHostController.navigate(Screen.QrCodeScanner.route) },
                navigateToTutorial = { navHostController.navigate(Screen.Tutorial.route) },
                onMissionClick = { missionTitle ->
                    navHostController.navigate(
                        Screen.MissionDetail.createRoute(missionTitle)
                    )
                },
            )
        }
    }
}