package com.clastic.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.clastic.model.PlasticKnowledge
import com.clastic.ui.theme.ClasticTheme

@Composable
internal fun PlasticKnowledgeComponent(
    onTypeClicked: (String) -> Unit,
    plasticType: PlasticKnowledge,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(color = Color(plasticType.colorHex.toColorInt()))
                .clickable { onTypeClicked(plasticType.tag) },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = plasticType.logoUrl,
                contentDescription = plasticType.tag,
                modifier = Modifier
                    .size(55.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = plasticType.tag,
            modifier = Modifier,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlasticKnowledgeComponentPreview() {
    ClasticTheme {
        PlasticKnowledgeComponent(
            onTypeClicked = {},
            plasticType = PlasticKnowledge(
                tag = "PP",
                name = "PolyP",
                description = "description",
                colorHex = "#D17021",
                coverUrl = "",
                logoUrl = "",
                productList = emptyList()
            )
        )
    }
}