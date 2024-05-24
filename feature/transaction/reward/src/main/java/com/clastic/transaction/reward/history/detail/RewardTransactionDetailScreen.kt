package com.clastic.transaction.reward.history.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.RewardTransaction
import com.clastic.transaction.reward.R
import com.clastic.transaction.reward.component.RewardTransactionDetailItem
import com.clastic.transaction.reward.component.item.RewardTransactionResultItem
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.utils.TimeUtil

@Composable
fun RewardTransactionDetailScreen(
    transactionId: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: RewardTransactionDetailViewModel = hiltViewModel<RewardTransactionDetailViewModel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingIndicator()
                viewModel.fetchRewardTransactionById(transactionId)
            }
            is UiState.Success -> {
                RewardTransactionDetailScreenContent(
                    rewardTransaction = uiState.data,
                    onBackPressed = onBackPressed,
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                Scaffold(
                    topBar = {
                        ClasticTopAppBar(
                            title = stringResource(R.string.reward_transaction),
                            onBackPressed = onBackPressed
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.White)
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RewardTransactionDetailScreenContent(
    rewardTransaction: RewardTransaction,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.reward_transaction),
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.transaction_created),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Verified,
                    tint = CyanPrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .size(150.dp)
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.transaction_info),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    RewardTransactionDetailItem(
                        fieldName = stringResource(R.string.transaction_code),
                        fieldValue = rewardTransaction.id
                    )
                    RewardTransactionDetailItem(
                        fieldName = stringResource(R.string.time),
                        fieldValue = TimeUtil.timestampToStringFormat(rewardTransaction.time)
                    )
                    Divider(
                        color = CyanPrimaryVariant,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .height(2.dp)
                    )
                    Text(
                        text = stringResource(R.string.reward_transaction_detail),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = CyanPrimary
                    )
                    rewardTransaction.rewardList.forEach { ownedReward ->
                        RewardTransactionResultItem(
                            rewardId = ownedReward.rewardId,
                            count = ownedReward.count
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RewardTransactionDetailScreenPreview() {
    ClasticTheme {
        RewardTransactionDetailScreenContent(
            rewardTransaction = RewardTransaction(
                id = "ASJDAIUJDIAOBD",
                totalPoints = 10000,
            ),
            onBackPressed = {}
        )
    }
}