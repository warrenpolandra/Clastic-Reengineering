package com.clastic.reward

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.clastic.model.Reward
import com.clastic.reward.component.RewardStoreItem
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun RewardStoreScreen(
    navigateToCart: () -> Unit,
    onRewardClick: (rewardId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: RewardStoreViewModel = hiltViewModel<RewardStoreViewModel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                }
            }
            is UiState.Success -> {
                RewardStoreScreenContent(
                    rewardList = uiState.data,
                    onRewardClick = onRewardClick,
                    navigateToCart = navigateToCart,
                    modifier = modifier
                )
            }
            is UiState.Error -> { showToast(context, uiState.errorMessage) }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
private fun RewardStoreScreenContent(
    rewardList: List<Reward>,
    navigateToCart: () -> Unit,
    onRewardClick: (rewardId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.store),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                },
                backgroundColor = CyanPrimary,
                actions = {
                    IconButton(onClick = navigateToCart) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            tint = Color.White,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 64.dp)
        ) {
            items(rewardList, key = { it.id }) { reward ->
                RewardStoreItem(
                    reward = reward,
                    onClick = onRewardClick,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RewardStoreScreenPreview() {
    ClasticTheme {
        RewardStoreScreenContent(
            rewardList = emptyList(),
            onRewardClick = {},
            navigateToCart = {}
        )
    }
}