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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.clastic.ui.AddRemoveRewardButton
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.PointTag
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.utils.NumberUtil

@Composable
fun RewardDetailScreen(
    rewardId: String,
    navigateToStore: () -> Unit,
    navigateToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: RewardDetailViewModel = hiltViewModel<RewardDetailViewModel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingIndicator()
                viewModel.getRewardById(rewardId)
            }
            is UiState.Success -> {
                val data = uiState.data
                RewardDetailScreenContent(
                    reward = data.reward,
                    rewardCount = data.count,
                    navigateToStore = navigateToStore,
                    onAddError = { showToast(context, context.getString(R.string.add_error)) },
                    onUpdateRewardCount = {
                        viewModel.addToCart(rewardId, it)
                        navigateToCart()
                    },
                    modifier = modifier
                )
            }
            is UiState.Error -> { showToast(context, uiState.errorMessage) }
        }
    }
}

@Composable
private fun RewardDetailScreenContent(
    reward: Reward,
    rewardCount: Int,
    onAddError: () -> Unit,
    onUpdateRewardCount: (count: Int) -> Unit,
    navigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    var itemCount by rememberSaveable { mutableIntStateOf(rewardCount) }

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
                    onRemove = { if (itemCount > 0) itemCount-- },
                    onAdd = {
                        if (itemCount < 100) itemCount++
                        else onAddError()
                    },
                    size = 40,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Button(
                    onClick = { onUpdateRewardCount(itemCount) },
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
            reward = Reward(
                description = "Dapatkan potongan harga sebesar 10% tanpa minimum belanja di semua gerai Starbucks Indonesia! Syarat dan ketentuan berlaku.",
                termsAndConditions = listOf(
                    "Berlaku di semua gerai Starbucks",
                    "Voucher hanya dapat digunakan 1x",
                    "Voucher dapat digunakan tanpa minimal transaksi",
                    "Berlaku untuk semua produk Starbucks"
                )
            ),
            rewardCount = 10,
            navigateToStore = {},
            onUpdateRewardCount = {},
            onAddError = {}
        )
    }
}