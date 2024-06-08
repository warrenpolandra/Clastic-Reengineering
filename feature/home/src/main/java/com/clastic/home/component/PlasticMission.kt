package com.clastic.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.home.R
import com.clastic.model.Mission
import com.clastic.ui.MissionCard
import com.clastic.ui.theme.ClasticTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PlasticMission(
    missionList: List<Mission>,
    onMissionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        pageCount = { missionList.size }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(R.string.plastic_mission),
                style = MaterialTheme.typography.h5.copy(Color.Black)
            )
            Icon(
               painter = painterResource(R.drawable.ic_campaign_white),
                tint = Color.Gray,
                contentDescription = null
            )
        }
        Text(
            text = stringResource(R.string.exchange_your_plastic_to_points),
            style = MaterialTheme.typography.subtitle1.copy(Color.Gray)
        )

        HorizontalPager(
            state = pagerState,
            key = { missionList[it].id }
        ) { index ->
            MissionCard(
                mission = missionList[index],
                onMissionCLick = onMissionClick,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlasticMissionPreview() {
    ClasticTheme {
        PlasticMission(
            missionList = emptyList(),
            onMissionClick = {}
        )
    }
}