package com.clastic.transaction.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.clastic.model.Impact
import com.clastic.transaction.mission.R
import com.clastic.ui.ImpactCard

@Composable
internal fun MissionTransactionDetailImpacts(
    missionImpacts: List<Impact>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(8.dp)
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
            modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                missionImpacts.chunked(2).forEach { impactChunk ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) { impactChunk.forEach { impact -> ImpactCard(impact = impact) } }
                }
            }
        }
    }
}