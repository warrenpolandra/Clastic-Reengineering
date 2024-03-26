package com.clastic.reengineering.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.clastic.model.BottomBarItem
import com.clastic.ui.R
import com.clastic.ui.theme.CyanPrimary

@Composable
fun BottomBar(
    currentMenu: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        contentColor = CyanPrimary,
        backgroundColor = Color.White
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = "Home",
                icon = ImageVector.vectorResource(R.drawable.ic_home)
            ),
            BottomBarItem(
                title = "Article",
                icon = ImageVector.vectorResource(R.drawable.ic_article)
            ),
            BottomBarItem(
                title = "Point Shop",
                icon = ImageVector.vectorResource(R.drawable.ic_shopping_cart)
            ),
            BottomBarItem(
                title = "Mission",
                icon = ImageVector.vectorResource(R.drawable.ic_campaign)
            ),
            BottomBarItem(
                title = "Profile",
                icon = ImageVector.vectorResource(R.drawable.ic_person)
            ),
        )
        navigationItems.map {
            BottomNavigationItem(
                selected = it.title == currentMenu,
                onClick = {
                    if (it.title != currentMenu) {
                        when (it.title) {
                            "Home" -> {
                                navController.popBackStack()
                                navController.navigate(Screen.Home.route)
                            }
                            "Article" -> {
                                navController.popBackStack()
                                navController.navigate(Screen.Article.route)
                            }
                            "Store" -> {
                                navController.popBackStack()
                                navController.navigate(Screen.Store.route)
                            }
                            "Mission" -> {
                                navController.popBackStack()
                                navController.navigate(Screen.Mission.route)
                            }
                            "Profile" -> {
                                navController.popBackStack()
                                navController.navigate(Screen.Mission.route)
                            }
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = it.icon, contentDescription = it.title
                    )
                },
                label = { Text(text = it.title) }
            )
        }
    }
}