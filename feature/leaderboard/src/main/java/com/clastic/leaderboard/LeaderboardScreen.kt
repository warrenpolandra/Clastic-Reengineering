package com.clastic.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.clastic.leaderboard.component.LeaderboardFooter
import com.clastic.leaderboard.component.LeaderboardHeader
import com.clastic.leaderboard.component.UserLeaderboardItem
import com.clastic.model.User
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.theme.ClasticTheme

@Composable
fun LeaderboardScreen(
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: LeaderboardViewModel = hiltViewModel<LeaderboardViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val userList by viewModel.userList.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val currentUserPosition by viewModel.currentUserPosition.collectAsState()

    LeaderboardScreenContent(
        isLoading = isLoading,
        userList = userList,
        currentUser = currentUser,
        currentUserPosition = currentUserPosition,
        navigateToProfile = navigateToProfile,
        modifier = modifier
    )
}

@Composable
private fun LeaderboardScreenContent(
    isLoading: Boolean,
    userList: List<User>,
    currentUser: User,
    currentUserPosition: Int,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.leaderboard),
                onBackPressed = navigateToProfile
            )
        },
        bottomBar = {
            LeaderboardFooter(
                position = currentUserPosition,
                user = currentUser
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                LeaderboardHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    itemsIndexed(userList, key = { _, user -> user.userId }) { index, user ->
                        UserLeaderboardItem(
                            position = index+1,
                            user = user
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
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

@Preview
@Composable
private fun LeaderboardScreenPreview() {
    ClasticTheme {
        LeaderboardScreenContent(
            isLoading = true,
            userList = emptyList(),
            currentUser = User(
                username = "Warren"
            ),
            currentUserPosition = 1,
            navigateToProfile = {}
        )
    }
}