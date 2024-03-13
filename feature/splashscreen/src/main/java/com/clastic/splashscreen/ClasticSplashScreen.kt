package com.clastic.splashscreen

import android.net.Uri
import android.os.Looper
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ClasticSplashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var splashVisible by rememberSaveable { mutableStateOf(true) }
    if (splashVisible) {
        val videoUri = "android.resource://com.clastic.reengineering/${R.raw.clastic_splash_screen}"
        AndroidView(
            factory = { innerContext ->
                VideoView(innerContext).apply {
                    setZOrderOnTop(true)
                    setVideoURI(Uri.parse(videoUri))
                    setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = false
                        mediaPlayer.start()

                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            mediaPlayer.stop()
                            splashVisible = false

                            navigateToLogin()
                        }, 2000)
                    }
                }
            },
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        )
    }
}