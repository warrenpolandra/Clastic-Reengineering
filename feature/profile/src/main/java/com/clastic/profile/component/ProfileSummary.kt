package com.clastic.profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
import com.clastic.profile.R
import com.clastic.ui.theme.CyanPrimary

@Composable
internal fun ProfileSummary(
    totalTransaction: Int,
    totalPlastic: Double,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(
                width = 2.dp,
                color = CyanPrimary,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = totalTransaction.toString(),
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(R.string.transaction_made),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            Divider(
                color = CyanPrimary,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.total_plastic_kg, "%.2f".format(totalPlastic)),
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(R.string.collected_plastic),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSummaryPreview() {
    ProfileSummary(
        totalTransaction = 20,
        totalPlastic = 200.198231
    )
}