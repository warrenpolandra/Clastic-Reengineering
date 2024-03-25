package com.clastic.model

import androidx.annotation.Keep
import androidx.compose.ui.graphics.vector.ImageVector

@Keep
data class BottomBarItem(
    val title: String,
    val icon: ImageVector
)
