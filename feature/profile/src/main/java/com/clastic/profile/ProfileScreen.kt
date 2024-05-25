package com.clastic.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.User
import com.clastic.profile.component.ProfileCard
import com.clastic.profile.component.ProfileMenu
import com.clastic.profile.component.ProfileSummary
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    navigateToPlasticTransactionHistory: () -> Unit,
    navigateToLeaderboard: () -> Unit,
    navigateToRewardTransactionHistory: () -> Unit,
    navigateToRewardInventory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    ProfileScreenContent(
        isLoading = isLoading,
        user = user,
        onLogoutClick = {
            viewModel.userLogout(
                onSignOutSuccess = onLogout,
                onSignOutFailed = { showToast(context, it) }
            )
        },
        navigateToLeaderboard = navigateToLeaderboard,
        navigateToPlasticTransactionHistory = navigateToPlasticTransactionHistory,
        navigateToRewardTransactionHistory = navigateToRewardTransactionHistory,
        navigateToRewardInventory = navigateToRewardInventory,
        modifier = modifier
    )
}

@Composable
private fun ProfileScreenContent(
    isLoading: Boolean,
    user: User,
    onLogoutClick: () -> Unit,
    navigateToLeaderboard: () -> Unit,
    navigateToPlasticTransactionHistory: () -> Unit,
    navigateToRewardTransactionHistory: () -> Unit,
    navigateToRewardInventory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                },
                backgroundColor = CyanPrimary
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                ProfileCard(
                    user = user,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                ProfileSummary(
                    totalTransaction = user.totalTransaction,
                    totalPlastic = user.totalPlastic,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                ProfileMenu(
                    title = stringResource(R.string.leaderboard),
                    icon = Icons.Default.EmojiEvents,
                    onClick = navigateToLeaderboard
                )
                ProfileMenu(
                    title = stringResource(R.string.plastic_transaction_history),
                    icon = Icons.AutoMirrored.Filled.List,
                    onClick = navigateToPlasticTransactionHistory
                )
                ProfileMenu(
                    title = stringResource(R.string.reward_transaction_history),
                    icon = Icons.Default.WorkspacePremium,
                    onClick = navigateToRewardTransactionHistory
                )
                ProfileMenu(
                    title = stringResource(R.string.reward_inventory),
                    icon = Icons.Default.Inventory,
                    onClick = navigateToRewardInventory
                )
                ProfileMenu(
                    title = stringResource(R.string.share_clastic_to_friends),
                    icon = Icons.Default.Share,
                    onClick = {
                        // TODO
                        print("")
                    }
                )
                ProfileMenu(
                    title = stringResource(R.string.settings),
                    icon = Icons.Default.Settings,
                    onClick = {
                        // TODO
                        print("")
                    },
                    modifier = Modifier
                        .drawBehind {
                            drawLine(
                                color = CyanPrimary,
                                start = Offset(0f, size.height+2f),
                                end = Offset(size.width, size.height+2f),
                                strokeWidth = 1.dp.toPx()/2
                            )
                        }
                )
                LogoutButton(
                    isEnabled = !isLoading,
                    onClick = onLogoutClick,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
private fun LogoutButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(13.dp),
        border = BorderStroke(2.dp, Color.Red),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PowerSettingsNew,
            contentDescription = stringResource(R.string.logout),
            tint = Color.Red,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = stringResource(R.string.logout),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ClasticTheme {
        ProfileScreenContent(
            isLoading = true,
            user = User(),
            onLogoutClick = {},
            navigateToLeaderboard = {},
            navigateToPlasticTransactionHistory = {},
            navigateToRewardTransactionHistory = {},
            navigateToRewardInventory = {}
        )
    }
}