package com.clastic.transaction.plastic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.transaction.plastic.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.utils.NumberUtil

@Composable
internal fun PlasticTransactionResultItem(
    plasticType: String,
    weight: Float,
    points: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Circle,
            tint = CyanPrimary,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(15.dp)
        )
        Column {
            Text(
                text = plasticType,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CyanPrimary
            )
            Text(
                text = stringResource(R.string.plastic_weight, weight),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(
                R.string.added_points,
                NumberUtil.formatNumberToGrouped(points)
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CyanPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlasticTransactionResultItemPreview() {
    ClasticTheme {
        PlasticTransactionResultItem(
            plasticType = "PET",
            weight = 12.0f,
            points = 36000,
        )
    }
}