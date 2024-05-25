package com.clastic.reward.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clastic.model.Reward
import com.clastic.reward.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.utils.NumberUtil

@Composable
internal fun RewardInventoryItem(
    reward: Reward,
    count: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(260.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                CyanPrimary,
                RoundedCornerShape(16.dp)
            )
            .clickable { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CyanPrimary)
        ) {
            AsyncImage(
                model = reward.imagePath,
                contentDescription = reward.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = reward.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.reward_count, NumberUtil.formatNumberToGrouped(count)),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun RewardInventoryItemPreview() {
    ClasticTheme {
        RewardInventoryItem(
            reward = Reward(
                name = "100% Discount Starbucks Voucher",
                imagePath = "",
            ),
            count = 3
        )
    }
}