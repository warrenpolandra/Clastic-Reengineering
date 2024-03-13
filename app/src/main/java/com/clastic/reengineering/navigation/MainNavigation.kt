package com.clastic.reengineering.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clastic.login.LoginScreen
import com.clastic.register.RegisterScreen
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
                navigateToHome = { /*TODO*/ }
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
    }
}