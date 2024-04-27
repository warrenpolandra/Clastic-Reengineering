package com.clastic.mission.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.Mission
import com.clastic.ui.MissionCard
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun ListMissionScreen(
    onMissionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListMissionViewModel = hiltViewModel<ListMissionViewModel>()
) {
    val missionList by viewModel.missionList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    ListMissionScreenContent(
        isLoading = isLoading,
        error = error,
        missionList = missionList,
        onMissionClick = onMissionClick,
        modifier = modifier
    )
}

@Composable
private fun ListMissionScreenContent(
    isLoading: Boolean,
    error: String,
    missionList: List<Mission>,
    onMissionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mission", color = Color.White) },
                backgroundColor = CyanPrimary
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                LazyColumn {
                    items(missionList, key = {it.title}) { mission ->
                        MissionCard(
                            mission = mission,
                            onMissionCLick = onMissionClick
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                )
            }
            if (error.isNotEmpty()) {
                Text(
                    text = error,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListMissionScreenPreview() {
    ClasticTheme {
        ListMissionScreenContent(
            isLoading = true,
            error = "Error fetching data",
            missionList = emptyList(),
            onMissionClick = {}
        )
    }
}