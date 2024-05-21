package com.clastic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberSmartRecord
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant2
import com.clastic.utils.NumberUtil

@Composable
fun PointTag(modifier: Modifier = Modifier, point: Int) {
    Box(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, CyanPrimary, RoundedCornerShape(20.dp))
                .background(color = Color.White)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Row(modifier = Modifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FiberSmartRecord,
                    contentDescription = "recycle icon",
                    modifier = Modifier,
                    tint = CyanPrimaryVariant2
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = NumberUtil.formatNumberToGrouped(point),
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary
                )
            }
        }
    }
}

@Preview()
@Composable
fun PointTagPreview() {
    ClasticTheme {
        PointTag(point = 500)
    }
}