package com.clastic.transaction.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.Mission
import com.clastic.model.MissionTransaction
import com.clastic.transaction.component.MissionTransactionDetailHeader
import com.clastic.transaction.component.MissionTransactionDetailImpacts
import com.clastic.transaction.component.MissionTransactionDetailInfo
import com.clastic.transaction.mission.R
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.utils.TimeUtil

@Composable
fun MissionTransactionDetailScreen(
    onBackPressed: () -> Unit,
    transactionId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: MissionTransactionDetailViewModel = hiltViewModel<MissionTransactionDetailViewModel>()
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Scaffold(
                    topBar = {
                        ClasticTopAppBar(
                            title = stringResource(R.string.mission_submission_detail),
                            onBackPressed = onBackPressed
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
                }
                viewModel.fetchMissionTransactionById(transactionId)
            }
            is UiState.Success -> {
                MissionTransactionDetailScreenContent(
                    mission = uiState.data.mission,
                    missionTransaction = uiState.data.missionTransaction,
                    onBackPressed = onBackPressed,
                    onUrlClickError = { showToast(context, context.getString(R.string.url_error)) }
                )
            }
            is UiState.Error -> {
                Scaffold(
                    topBar = {
                        ClasticTopAppBar(
                            title = stringResource(R.string.mission_submission),
                            onBackPressed = onBackPressed
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
private fun MissionTransactionDetailScreenContent(
    mission: Mission,
    missionTransaction: MissionTransaction,
    onUrlClickError: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.mission_submission_detail),
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            item { MissionTransactionDetailHeader() }
            item { MissionTransactionDetailInfo(
                isPictureSubmission = missionTransaction.isPicture,
                missionTransactionId = missionTransaction.id,
                missionReward = mission.reward,
                missionTransactionTime = missionTransaction.time,
                submissionUrl = missionTransaction.submissionUrl,
                onUrlClickError = onUrlClickError
            )}
            item { MissionTransactionDetailImpacts(missionImpacts = mission.impacts) }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
private fun MissionTransactionDetailScreenPreview() {
    ClasticTheme {
        MissionTransactionDetailScreenContent(
            mission = Mission(
                reward = 1000
            ),
            missionTransaction = MissionTransaction(
                id = "AFIUDBOIUBPAIUBAPSUI",
                userId = "",
                time = TimeUtil.getTimestamp(),
                submissionUrl = "https://google.com",
                totalPoints = 500,
                missionId = "",
                isPicture = false
            ),
            onBackPressed = {},
            onUrlClickError = {}
        )
    }
}