package com.clastic.transaction.reward.history.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.RewardTransaction
import com.clastic.transaction.reward.R
import com.clastic.transaction.reward.component.RewardTransactionHistoryItem
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme

@Composable
fun RewardTransactionHistoryScreen(
    navigateToProfile: () -> Unit,
    navigateToRewardTransactionDetail: (transactionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: RewardTransactionHistoryViewModel = hiltViewModel<RewardTransactionHistoryViewModel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Scaffold(
                    topBar = {
                        ClasticTopAppBar(
                            title = stringResource(R.string.reward_transaction_history),
                            onBackPressed = navigateToProfile
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
                    ) { LoadingIndicator() }
                }            }
            is UiState.Success -> {
                RewardTransactionListScreenContent(
                    rewardTransactionList = uiState.data,
                    onTransactionClicked = navigateToRewardTransactionDetail,
                    navigateToProfile = navigateToProfile,
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                Scaffold(
                    topBar = {
                        ClasticTopAppBar(
                            title = stringResource(R.string.reward_transaction),
                            onBackPressed = navigateToProfile
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
private fun RewardTransactionListScreenContent(
    rewardTransactionList: List<RewardTransaction>,
    onTransactionClicked: (transactionId: String) -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.reward_transaction_history),
                onBackPressed = navigateToProfile
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            items(rewardTransactionList, key = { it.id }) { rewardTransaction ->
                RewardTransactionHistoryItem(
                    rewardTransaction = rewardTransaction,
                    onClick = { onTransactionClicked(rewardTransaction.id) },
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RewardTransactionListScreenPreview() {
    ClasticTheme {
        RewardTransactionListScreenContent(
            rewardTransactionList = emptyList(),
            onTransactionClicked = {},
            navigateToProfile = {}
        )
    }
}