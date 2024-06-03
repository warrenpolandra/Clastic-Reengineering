package com.clastic.transaction.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanTextField
import com.clastic.utils.NumberUtil

@Composable
internal fun MissionTransactionHistoryCard(
    date: String,
    points: Int,
    transactionId: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(3.dp, CyanTextField),
        backgroundColor = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = date,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.transaction_code_id, transactionId),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.added_points, NumberUtil.formatNumberToGrouped(points)),
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.detail),
                    color = CyanPrimary,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = CyanPrimary,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MissionTransactionHistoryCardPreview() {
    ClasticTheme {
        MissionTransactionHistoryCard(
            date = "Senin 21 Juli 2023",
            points = 50000,
            transactionId = "RW9dTvBBaQJWCTnQVDb0",
            onClick = {}
        )
    }
}