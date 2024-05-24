package com.clastic.transaction.reward.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.OrderedReward
import com.clastic.model.Reward
import com.clastic.transaction.reward.R
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.utils.NumberUtil

@Composable
internal fun RewardTransactionResultItem(
    rewardId: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    val viewModel: RewardTransactionResultItemViewmodel = hiltViewModel<RewardTransactionResultItemViewmodel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> { viewModel.fetchRewardById(rewardId) }
            is UiState.Success -> {
                RewardTransactionResultItemContent(
                    orderedReward = OrderedReward(
                        reward = uiState.data,
                        count = count
                    ),
                    modifier = modifier
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
private fun RewardTransactionResultItemContent(
    orderedReward: OrderedReward,
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
        Column(
            modifier = Modifier.width(240.dp)
        ) {
            Text(
                text = orderedReward.reward.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = CyanPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(R.string.reward_count, orderedReward.count),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(
                R.string.points_value,
                NumberUtil.formatNumberToGrouped(orderedReward.count * orderedReward.reward.value)
            ),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = CyanPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RewardTransactionResultItemPreview() {
    ClasticTheme {
        RewardTransactionResultItemContent(
            orderedReward = OrderedReward(
                reward = Reward(
                    name = "Voucher starbucks discount 10%",
                    id = "",
                    value = 5000
                ),
                count = 2
            )
        )
    }
}