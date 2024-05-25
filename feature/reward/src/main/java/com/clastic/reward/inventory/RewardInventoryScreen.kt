package com.clastic.reward.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.OrderedReward
import com.clastic.reward.R
import com.clastic.reward.component.RewardInventoryItem
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme

@Composable
fun RewardInventoryScreen(
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: RewardInventoryViewModel = hiltViewModel<RewardInventoryViewModel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingIndicator()
                viewModel.fetchOwnedReward()
            }
            is UiState.Success -> {
                RewardInventoryScreenContent(
                    ownedRewardList = uiState.data,
                    navigateToProfile = navigateToProfile,
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                Scaffold(
                    topBar = {
                        ClasticTopAppBar(
                            title = stringResource(R.string.reward_inventory),
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
private fun RewardInventoryScreenContent(
    ownedRewardList: List<OrderedReward>,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.reward_inventory),
                onBackPressed = navigateToProfile
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 64.dp)
        ) {
            items(ownedRewardList, key = { it.reward.id }) { orderedReward ->
                RewardInventoryItem(
                    reward = orderedReward.reward,
                    count = orderedReward.count,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RewardInventoryScreenPreview() {
    ClasticTheme {
        RewardInventoryScreenContent(
            ownedRewardList = emptyList(),
            navigateToProfile = {}
        )
    }
}