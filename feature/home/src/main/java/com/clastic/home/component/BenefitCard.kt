package com.clastic.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanTextField

@Composable
internal fun BenefitCard(
    benefitText: String,
    cardNumber: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .width(105.dp)
            .height(140.dp)
            .border(
                width = 1.dp,
                CyanTextField,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = benefitText,
            color = Color.Black,
            fontSize = 13.sp
        )
        Text(
            text = cardNumber.toString(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.h5.copy(
                color = CyanPrimary,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BenefitCardPreview() {
    ClasticTheme {
        BenefitCard(
            benefitText = "Meningkatkan Kesehatan Lingkungan",
            cardNumber = 1
        )
    }
}