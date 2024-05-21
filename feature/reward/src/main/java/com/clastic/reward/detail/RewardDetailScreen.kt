package com.clastic.reward.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.ui.theme.ClasticTheme

@Composable
fun RewardDetailScreen(
    rewardId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: RewardDetailViewModel = hiltViewModel<RewardDetailViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
}

@Composable
private fun RewardDetailScreenContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun RewardDetailScreenPreview() {
    ClasticTheme {

    }
}