package com.clastic.mission.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.mission.R
import com.clastic.mission.component.MissionBottomBar
import com.clastic.model.Mission
import com.clastic.ui.BannerWithGradient
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.ImpactCard
import com.clastic.ui.PointTag
import com.clastic.ui.RecycleTag
import com.clastic.ui.theme.ClasticTheme

@Composable
fun MissionDetailScreen(
    missionId: String,
    navigateToMissionList: () -> Unit,
    onMissionSubmitClick: (missionId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel: MissionDetailViewModel = hiltViewModel<MissionDetailViewModel>()
    val mission by viewModel.mission.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchMissionById(
            missionId = missionId,
            onFetchFailed = { error ->
                showToast(context, error)
            }
        )
    }

    MissionDetailScreenContent(
        mission = mission,
        navigateToMissionList = navigateToMissionList,
        onMissionSubmitClick = onMissionSubmitClick,
        modifier = modifier
    )
}

@Composable
private fun MissionDetailScreenContent(
    mission: Mission,
    navigateToMissionList: () -> Unit,
    onMissionSubmitClick: (missionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = { MissionBottomBar(onJoinButtonClick = { onMissionSubmitClick(mission.id) }) },
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.mission_detail),
                onBackPressed = navigateToMissionList
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 10.dp,
                                bottomStart = 10.dp
                            )
                        )
                ) {
                    BannerWithGradient(imageUrl = mission.imageUrl)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(8.dp), contentAlignment = Alignment.TopEnd
                    ) {
                        RecycleTag(modifier = Modifier, tag = mission.tags)
                    }
                    Box(
                        contentAlignment = Alignment.BottomStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = mission.title,
                                style = MaterialTheme.typography.h5.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                            )
                            PointTag(modifier = Modifier, point = mission.reward)
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = mission.description,
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                    )
                    Text(
                        text = stringResource(R.string.mission_objective),
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                    )
                    mission.objectives.forEachIndexed { index, objective ->
                        Text(
                            text = "${index + 1}. $objective",
                            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                        )
                    }
                    Text(
                        text = stringResource(R.string.finish_mission),
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .alpha(0.6f)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.yearly_impact),
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(R.string.equivalent_mission_activities),
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(12.dp)
                        ) {
                            mission.impacts.chunked(2).forEach { impactChunk ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    impactChunk.forEach { impact ->
                                        ImpactCard(impact = impact)
                                    }
                                }
                            }
                        }
                    }
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
private fun MissionDetailScreenPreview() {
    ClasticTheme {
        MissionDetailScreenContent(
            mission = Mission(
                id = "",
                title = "Plastic bags diet",
                description = "Welcome to this mission! In this mission, you have to stop using plastic bags because of its pollution, harmful, high carbon, and other negative impacts!",
                objectives = listOf(
                    "Use reusable bags when buying foods, items, etc.",
                    "Recycle your plastic bags collection if any at home."
                ),
                imageUrl = "Plastic bags diet",
                tags = listOf("HDPEM", "PET"),
                reward = 300,
                impacts = emptyList(),
                endDate = 0L
            ),
            navigateToMissionList = {},
            onMissionSubmitClick = {}
        )
    }
}