package com.clastic.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimaryVariant2

@Composable
internal fun ExchangeStep(
    stepNumber: Int,
    stepText: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clip(CircleShape)
                .background(CyanPrimaryVariant2),
        ) {
            Text(
                text = stepNumber.toString(),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            text = stepText,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExchangeStepPreview() {
    ClasticTheme {
        ExchangeStep(
            stepNumber = 1,
            stepText = "Click this"
        )
    }
}