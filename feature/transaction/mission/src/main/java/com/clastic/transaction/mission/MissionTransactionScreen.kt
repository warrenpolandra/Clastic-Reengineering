package com.clastic.transaction.mission

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.Mission
import com.clastic.transaction.component.SubmitMissionBottomBar
import com.clastic.transaction.component.SubmitMissionDescription
import com.clastic.transaction.component.SubmitMissionImageInput
import com.clastic.transaction.component.SubmitMissionInstructions
import com.clastic.transaction.component.SubmitMissionTextField
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.LoadingIndicator
import com.clastic.ui.UiState
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun MissionTransactionScreen(
    onBackPressed: () -> Unit,
    missionId: String,
    navigateToMissionTransactionDetail: (transactionId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel: MissionTransactionViewModel = hiltViewModel<MissionTransactionViewModel>()
    val linkSubmission by viewModel.linkSubmission.collectAsState()
    val linkSubmissionEnabled by viewModel.linkSubmissionEnabled.collectAsState()
    val imageSubmission by viewModel.imageUri.collectAsState()
    val imageSubmissionEnabled by viewModel.imageSubmissionEnabled.collectAsState()
    val isTnCChecked by viewModel.isTnCChecked.collectAsState()
    val buttonEnabled by viewModel.buttonEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.setImageUri(uri) }
    )

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
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
                        LoadingIndicator()
                    }
                }
                viewModel.fetchMissionById(missionId)
            }
            is UiState.Success -> {
                MissionTransactionScreenContent(
                    isLoading = isLoading,
                    mission = uiState.data,
                    linkSubmission = linkSubmission,
                    linkSubmissionEnabled = linkSubmissionEnabled,
                    imageSubmission = imageSubmission,
                    imageSubmissionEnabled = imageSubmissionEnabled,
                    onImageChange = { imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )},
                    onRemoveImage = { viewModel.setImageUri(null) },
                    buttonEnabled = buttonEnabled,
                    isTnCChecked = isTnCChecked,
                    onTnCChange = { viewModel.setTnCChecked() },
                    onLinkSubmissionChange = { viewModel.setLinkSubmission(it) },
                    onSubmitClick = { viewModel.submitMission(
                        missionId = missionId,
                        imageUri = imageSubmission,
                        linkSubmission = linkSubmission,
                        totalPoints = uiState.data.reward,
                        onPostSuccess = navigateToMissionTransactionDetail,
                        onPostFailed = { showToast(context, it) }
                    ) },
                    onBackPressed = onBackPressed,
                    modifier = modifier
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
private fun MissionTransactionScreenContent(
    isLoading: Boolean,
    mission: Mission,
    linkSubmission: String,
    linkSubmissionEnabled: Boolean,
    onLinkSubmissionChange: (String) -> Unit,
    imageSubmission: Uri?,
    imageSubmissionEnabled: Boolean,
    onImageChange: () -> Unit,
    onRemoveImage: () -> Unit,
    buttonEnabled: Boolean,
    isTnCChecked: Boolean,
    onTnCChange: () -> Unit,
    onBackPressed: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.mission_submission),
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            SubmitMissionBottomBar(
                buttonEnabled = buttonEnabled,
                isTnCChecked = isTnCChecked,
                onTnCChange = onTnCChange,
                onSubmitClick = onSubmitClick
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item { SubmitMissionDescription(mission) }
            item { SubmitMissionInstructions() }
            item { SubmitMissionTextField(
                linkSubmission = linkSubmission,
                linkSubmissionEnabled = linkSubmissionEnabled,
                onLinkSubmissionChange = onLinkSubmissionChange
            )}
            item {
                Text(
                    text = stringResource(R.string.mission_or),
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
            item {
                SubmitMissionImageInput(
                    imageSubmission = imageSubmission,
                    imageSubmissionEnabled = imageSubmissionEnabled,
                    onRemoveImage = onRemoveImage,
                    onImageChange = onImageChange,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
        if (isLoading) LoadingIndicator()
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
private fun MissionTransactionScreenPreview() {
    ClasticTheme {
        MissionTransactionScreenContent(
            mission = Mission(
                title = "Plastic bags diet!",
                reward = 10000,
                objectives = listOf(
                    "Use reusable bags when buying foods, items, etc.",
                    "Recycle your plastic bags collection if any at home."
                )
            ),
            linkSubmission = "",
            linkSubmissionEnabled = true,
            imageSubmission = Uri.EMPTY,
            imageSubmissionEnabled = true,
            onRemoveImage = {},
            onImageChange = {},
            buttonEnabled = true,
            isTnCChecked = true,
            onTnCChange = {},
            onLinkSubmissionChange = {},
            onSubmitClick = {},
            onBackPressed = {},
            isLoading = true
        )
    }
}