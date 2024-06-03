package com.clastic.transaction.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.MissionTransaction
import com.clastic.transaction.component.MissionTransactionHistoryCard
import com.clastic.transaction.mission.R
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.utils.TimeUtil

@Composable
fun MissionTransactionHistoryScreen(
    onBackPressed: () -> Unit,
    onTransactionClicked: (transactionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MissionTransactionHistoryViewModel = hiltViewModel<MissionTransactionHistoryViewModel>()
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
                viewModel.fetchMissionTransactionHistory()
            }
            is UiState.Success -> {
                MissionTransactionHistoryScreenContent(
                    missionTransactionList = uiState.data,
                    onBackPressed = onBackPressed,
                    onTransactionClicked = onTransactionClicked
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
private fun MissionTransactionHistoryScreenContent(
    missionTransactionList: List<MissionTransaction>,
    onBackPressed: () -> Unit,
    onTransactionClicked: (transactionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.mission_submission_history),
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 12.dp)
            ) {
                missionTransactionList.map { missionTransaction ->
                    item {
                        MissionTransactionHistoryCard(
                            date = TimeUtil.timestampToStringFormat(missionTransaction.time),
                            points = missionTransaction.totalPoints,
                            transactionId = missionTransaction.id,
                            onClick = { onTransactionClicked(missionTransaction.id) },
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MissionTransactionHistoryScreenPreview() {
    ClasticTheme {
        MissionTransactionHistoryScreenContent(
            missionTransactionList = emptyList(),
            onBackPressed = {},
            onTransactionClicked = {}
        )
    }
}