package com.clastic.transaction.plastic.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.transaction.plastic.PlasticTransaction
import com.clastic.transaction.plastic.R
import com.clastic.transaction.plastic.component.PlasticTransactionHistoryCard
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.theme.ClasticTheme
import com.clastic.utils.TimeUtil

@Composable
fun PlasticTransactionHistoryScreen(
    onTransactionClicked: (String) -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<PlasticTransactionHistoryViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val plasticTransactionList by viewModel.plasticTransactionList.collectAsState()

    PlasticTransactionHistoryScreenContent(
        isLoading = isLoading,
        plasticTransactionList = plasticTransactionList,
        navigateToProfile = navigateToProfile,
        onTransactionClicked = onTransactionClicked,
        modifier = modifier
    )
}

@Composable
private fun PlasticTransactionHistoryScreenContent(
    isLoading: Boolean,
    plasticTransactionList: List<PlasticTransaction>,
    navigateToProfile: () -> Unit,
    onTransactionClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.plastic_transaction_history),
                onBackPressed = navigateToProfile
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
                plasticTransactionList.map {
                    item {
                        PlasticTransactionHistoryCard(
                            date = TimeUtil.timestampToStringFormat(it.date),
                            dropPointId = it.dropPointId,
                            points = it.totalPoints,
                            onClick = { onTransactionClicked(it.id) },
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { if (isLoading) { CircularProgressIndicator(modifier = Modifier.size(64.dp)) } }
    }
}

@Preview
@Composable
private fun PlasticTransactionScreenPreview() {
    ClasticTheme {
        PlasticTransactionHistoryScreenContent(
            isLoading = true,
            plasticTransactionList = emptyList(),
            navigateToProfile = {},
            onTransactionClicked = {}
        )
    }
}