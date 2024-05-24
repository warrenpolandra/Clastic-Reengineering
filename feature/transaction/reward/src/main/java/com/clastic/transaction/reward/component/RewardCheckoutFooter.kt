package com.clastic.transaction.reward.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.transaction.reward.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.utils.NumberUtil

@Composable
internal fun RewardCheckoutFooter(
    ownedPoints: Int,
    totalPoints: Int,
    onCreateTransaction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(CyanPrimary)
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Text(
                text = stringResource(R.string.total_transaction),
                color = Color.White
            )
            Text(
                text = stringResource(R.string.points_value, NumberUtil.formatNumberToGrouped(totalPoints)),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                text = if (ownedPoints >= totalPoints) stringResource(R.string.your_points, NumberUtil.formatNumberToGrouped(ownedPoints))
                            else stringResource(R.string.your_points_not_enough, NumberUtil.formatNumberToGrouped(ownedPoints)),
                color = if (ownedPoints >= totalPoints) Color.White else Color.Red
            )
        }
        Button(
            enabled = ownedPoints >= totalPoints,
            border = BorderStroke(2.dp, Color.White),
            onClick = onCreateTransaction,
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = stringResource(R.string.buy),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun RewardCheckoutFooterPreview() {
    ClasticTheme {
        RewardCheckoutFooter(
            totalPoints = 10000,
            ownedPoints = 91000,
            onCreateTransaction = {}
        )
    }
}