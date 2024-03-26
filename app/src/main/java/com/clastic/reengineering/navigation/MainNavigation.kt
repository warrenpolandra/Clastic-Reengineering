package com.clastic.reengineering.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var isAuthenticated by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (isAuthenticated) BottomBar(currentMenu = "Home", navController = navHostController)
        }
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
                        isAuthenticated = true
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
                        isAuthenticated = true
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
                        isAuthenticated = true
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
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
}