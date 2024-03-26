package com.clastic.ui.typhography

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleWithShadow(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            color = Color.DarkGray,
            fontSize = 16.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .offset(x = 2.dp, y = 2.dp)
                .alpha(0.75f)
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 2,
            modifier = Modifier,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
        )
    }
}