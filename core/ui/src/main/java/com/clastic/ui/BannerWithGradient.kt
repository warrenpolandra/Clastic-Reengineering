package com.clastic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimaryVariant2

@Composable
fun BannerWithGradient(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(
                RoundedCornerShape(
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp
                )
            )
            .blur(radius = 1.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToScale(
                        0.8f,
                        0.8f,
                        0.8f,
                        1f
                    )
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 10.dp,
                        bottomStart = 10.dp
                    )
                )
                .shadow(elevation = 2.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp
                ))
                .alpha(0.5f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            CyanPrimaryVariant2,
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = 400f
                    )
                )
        )
    }
}

@Preview(showBackground = false)
@Composable
fun BannerWithGradientPreview() {
    ClasticTheme {
        BannerWithGradient(
            ""
        )
    }
}