package com.clastic.transaction.reward

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.OrderedReward
import com.clastic.model.Reward
import com.clastic.transaction.reward.component.RewardCheckoutFooter
import com.clastic.transaction.reward.component.RewardCheckoutItem
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun RewardTransactionScreen(
    navigateToStore: () -> Unit,
    navigateToRewardTransactionDetail: (transactionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: RewardTransactionViewModel = hiltViewModel<RewardTransactionViewModel>()
    val user by viewModel.user.collectAsState()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> { LoadingIndicator() }
            is UiState.Success -> {
                if (uiState.data.isNotEmpty()) {
                    RewardTransactionScreenContent(
                        navigateToStore = navigateToStore,
                        orderedRewardList = uiState.data,
                        onRewardCountChanged = { rewardId, count ->
                            viewModel.updateRewardCount(
                                rewardId,
                                count,
                                onAddError = { showToast(context, context.getString(R.string.add_error)) }
                            )
                        },
                        totalPoints = uiState.data.sumOf { it.reward.value * it.count },
                        ownedPoints = user.points,
                        onCreateTransaction = { orderedRewardList, totalPoints ->
                            viewModel.createRewardTransaction(
                                orderedRewardList = orderedRewardList,
                                totalPoints = totalPoints,
                                userId = user.email,
                                onPostSuccess = navigateToRewardTransactionDetail,
                                onPostFailed = { showToast(context, it) }
                            )
                        },
                        modifier = modifier
                    )
                } else {
                    Scaffold(
                        topBar = {
                            ClasticTopAppBar(
                                title = stringResource(R.string.reward_transaction),
                                onBackPressed = navigateToStore
                            )
                        },
                        modifier = modifier
                    ) { innerPadding ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(innerPadding)
                        ) {
                            Text(
                                text = stringResource(R.string.no_reward),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
            is UiState.Error -> { showToast(context, uiState.errorMessage) }
        }
    }
}

@Composable
private fun RewardTransactionScreenContent(
    navigateToStore: () -> Unit,
    totalPoints: Int,
    ownedPoints: Int,
    orderedRewardList: List<OrderedReward>,
    onRewardCountChanged: (rewardId: String, count: Int) -> Unit,
    onCreateTransaction: (List<OrderedReward>, totalPoints: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.reward_transaction),
                onBackPressed = navigateToStore
            )
        },
        bottomBar = {
            RewardCheckoutFooter(
                ownedPoints = ownedPoints,
                totalPoints = totalPoints,
                onCreateTransaction = { onCreateTransaction(
                    orderedRewardList,
                    totalPoints
                )}
            )
        },
        modifier = modifier
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.reward_transaction),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = CyanPrimary,
                modifier = Modifier.padding(top = 32.dp)
            )
            Text(
                text = stringResource(R.string.reward_transaction_instruction),
                color = Color.Black,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(orderedRewardList, key = { it.reward.id }) { orderedReward ->
                    RewardCheckoutItem(
                        reward = orderedReward.reward,
                        count = orderedReward.count,
                        onRewardCountChanged = onRewardCountChanged
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
@Preview
private fun RewardTransactionScreenPreview() {
    ClasticTheme {
        RewardTransactionScreenContent(
            orderedRewardList =
                listOf(OrderedReward(reward = Reward(), 2))
            ,
            onRewardCountChanged = { _, _ -> },
            navigateToStore = {},
            onCreateTransaction = { _, _ -> },
            totalPoints = 0,
            ownedPoints = 0
        )
    }
}