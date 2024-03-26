package com.clastic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun RecycleTag(
    tag: List<String>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.White)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_recycle_blue),
                    contentDescription = "recycle icon",
                    modifier = Modifier,
                    tint = CyanPrimary
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = tag.toString(),
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecycleTagPreview() {
    ClasticTheme {
        RecycleTag(tag = listOf("PET", "HDPE", "PP"))
    }
}