package com.clastic.reengineering.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.google.errorprone.annotations.Keep

@Keep
data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)