package com.clastic.reengineering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.clastic.reengineering.navigation.MainNavigation
import com.clastic.ui.theme.ClasticTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClasticTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    MainNavigation()
                }
            }
        }
    }
}