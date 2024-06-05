package com.clastic.transaction.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.model.Mission
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.utils.NumberUtil

@Composable
internal fun SubmitMissionDescription(
    mission: Mission,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.description),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        MissionTransactionDetailItem(
            fieldName = stringResource(R.string.mission_title),
            fieldValue = mission.title
        )
        MissionTransactionDetailItem(
            fieldName = stringResource(R.string.mission_points),
            fieldValue = NumberUtil.formatNumberToGrouped(mission.reward)
        )
        Text(
            text = stringResource(R.string.mission_objective),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Black),
            modifier = Modifier.padding(top = 16.dp)
        )
        mission.objectives.forEachIndexed { index, objective ->
            Text(
                text = "${index + 1}. $objective",
                style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitMissionDescriptionPreview() {
    ClasticTheme {
        SubmitMissionDescription(
            mission = Mission(
                title = "Plastic bags diet!",
                reward = 10000,
                objectives = listOf(
                    "Use reusable bags when buying foods, items, etc.",
                    "Recycle your plastic bags collection if any at home."
                )
            )
        )
    }
}