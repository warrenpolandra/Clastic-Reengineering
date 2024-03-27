package com.clastic.reengineering.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.clastic.ui.R
import com.clastic.ui.theme.CyanPrimary

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        contentColor = CyanPrimary,
        backgroundColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            BottomBarItem(
                title = "Home",
                icon = ImageVector.vectorResource(R.drawable.ic_home),
                screen = Screen.Home
            ),
            BottomBarItem(
                title = "Article",
                icon = ImageVector.vectorResource(R.drawable.ic_article),
                screen = Screen.Article
            ),
            BottomBarItem(
                title = "Point Shop",
                icon = ImageVector.vectorResource(R.drawable.ic_shopping_cart),
                screen = Screen.Store
            ),
            BottomBarItem(
                title = "Mission",
                icon = ImageVector.vectorResource(R.drawable.ic_campaign),
                screen = Screen.Mission
            ),
            BottomBarItem(
                title = "Profile",
                icon = ImageVector.vectorResource(R.drawable.ic_person),
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            BottomNavigationItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.popBackStack()
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon, contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) }
            )
        }
    }
}