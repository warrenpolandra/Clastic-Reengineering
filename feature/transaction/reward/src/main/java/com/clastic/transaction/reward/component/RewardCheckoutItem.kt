package com.clastic.transaction.reward.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clastic.model.Reward
import com.clastic.transaction.reward.R
import com.clastic.ui.AddRemoveRewardButton
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.utils.NumberUtil

@Composable
internal fun RewardCheckoutItem(
    reward: Reward,
    count: Int,
    onRewardCountChanged: (rewardId: String, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, CyanPrimary, RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(260.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .size(64.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .border(1.dp, CyanPrimary, CircleShape)
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = reward.imagePath,
                    contentDescription = reward.name,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = reward.name,
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.points_value, NumberUtil.formatNumberToGrouped(reward.value)),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.points_subtotal, NumberUtil.formatNumberToGrouped(reward.value * count)),
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary
                )
            }
        }
        AddRemoveRewardButton(
            size = 22,
            itemCount = count,
            onRemove = { onRewardCountChanged(reward.id, count - 1) },
            onAdd = { onRewardCountChanged(reward.id, count + 1) },
            modifier = Modifier.padding(end = 12.dp)
        )
    }
}

@Preview
@Composable
private fun RewardCheckoutItemPreview() {
    ClasticTheme {
        RewardCheckoutItem(
            reward =  Reward(
                name = "10% Starbucks discount",
                value = 10000
            ),
            count = 2,
            onRewardCountChanged = { _, _ -> }
        )
    }
}