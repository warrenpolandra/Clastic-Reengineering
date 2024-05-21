package com.clastic.reward.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.clastic.model.Reward
import com.clastic.reward.R
import com.clastic.reward.component.AddRemoveRewardButton
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.PointTag
import com.clastic.ui.theme.ClasticTheme
import com.clastic.utils.NumberUtil

@Composable
fun RewardDetailScreen(
    rewardId: String,
    navigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: RewardDetailViewModel = hiltViewModel<RewardDetailViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val reward by viewModel.reward.collectAsState()
    val itemCount by viewModel.itemCount.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchRewardById(
            rewardId = rewardId,
            onFetchFailed = { error -> showToast(context, error) }
        )
    }

    RewardDetailScreenContent(
        isLoading = isLoading,
        reward = reward,
        itemCount = itemCount,
        navigateToStore = navigateToStore,
        onAddItem = { viewModel.increaseItemCount(onIncreaseFailed = { showToast(context, it) }) },
        onRemoveItem = { viewModel.reduceItemCount(onReduceFailed = { showToast(context, it) }) },
        onAddToCart = { viewModel.addToCart(onAddFailed = { showToast(context, it) }) },
        modifier = modifier
    )
}

@Composable
private fun RewardDetailScreenContent(
    isLoading: Boolean,
    reward: Reward,
    itemCount: Int,
    onAddItem: () -> Unit,
    onRemoveItem: () -> Unit,
    onAddToCart: () -> Unit,
    navigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.reward_detail),
                onBackPressed = navigateToStore
            )
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AddRemoveRewardButton(
                    itemCount = itemCount,
                    onRemove = onRemoveItem,
                    onAdd = onAddItem,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                )
                Button(
                    onClick = onAddToCart,
                    enabled = itemCount > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart, NumberUtil.formatNumberToGrouped(reward.value * itemCount)),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                AsyncImage(
                    model = reward.imagePath,
                    contentDescription = reward.name,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
                PointTag(
                    point = reward.value,
                    modifier = Modifier
                        .offset(
                            x = (-16).dp,
                            y = 16.dp
                        )
                        .align(Alignment.TopEnd)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.h5.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = reward.description,
                    style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                )
                Text(
                    text = stringResource(R.string.reward_terms_and_condition),
                    style = MaterialTheme.typography.h5.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                reward.termsAndConditions.forEachIndexed { index, tnc ->
                    Text(
                        text = "${index + 1}. $tnc",
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
private fun RewardDetailScreenPreview() {
    ClasticTheme {
        RewardDetailScreenContent(
            isLoading = true,
            reward = Reward(
                description = "Dapatkan potongan harga sebesar 10% tanpa minimum belanja di semua gerai Starbucks Indonesia! Syarat dan ketentuan berlaku.",
                termsAndConditions = listOf(
                    "Berlaku di semua gerai Starbucks",
                    "Voucher hanya dapat digunakan 1x",
                    "Voucher dapat digunakan tanpa minimal transaksi",
                    "Berlaku untuk semua produk Starbucks"
                )
            ),
            itemCount = 10,
            navigateToStore = {},
            onRemoveItem = {},
            onAddItem = {},
            onAddToCart = {}
        )
    }
}