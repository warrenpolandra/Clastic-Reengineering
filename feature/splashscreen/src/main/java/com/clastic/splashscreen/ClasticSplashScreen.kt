package com.clastic.splashscreen

import android.net.Uri
import android.os.Looper
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.ui.theme.ClasticTheme

@Composable
fun ClasticSplashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: SplashScreenViewModel = hiltViewModel<SplashScreenViewModel>()
    val isUserLoggedIn = viewModel.isUserLoggedIn()

    SplashScreenContent(
        isUserLoggedIn = isUserLoggedIn,
        navigateToHome = navigateToHome,
        navigateToLogin = navigateToLogin,
        modifier = modifier
    )
}

@Composable
private fun SplashScreenContent(
    isUserLoggedIn: Boolean,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    ) {
    var splashVisible by rememberSaveable { mutableStateOf(true) }
    if (splashVisible) {
        val videoUri = "android.resource://com.clastic.reengineering/${R.raw.clastic_splash_screen}"
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
            ,
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { innerContext ->
                    VideoView(innerContext).apply {
                        setBackgroundColor(Color.White.toArgb())
                        setZOrderOnTop(true)
                        setVideoURI(Uri.parse(videoUri))
                        setOnPreparedListener { mediaPlayer ->
                            mediaPlayer.isLooping = false
                            mediaPlayer.start()

                            android.os.Handler(Looper.getMainLooper()).postDelayed({
                                mediaPlayer.stop()
                                splashVisible = false
                                if (isUserLoggedIn) {
                                    navigateToHome()
                                } else {
                                    navigateToLogin()
                                }
                            }, 2000)
                        }
                    }
                },
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun SplashScreenPreview() {
    ClasticTheme {
        SplashScreenContent(
            isUserLoggedIn = true,
            navigateToHome = {},
            navigateToLogin = {}
        )
    }
}