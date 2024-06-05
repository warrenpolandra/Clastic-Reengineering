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
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.ClasticTheme

@Composable
internal fun SubmitMissionInstructions(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.submit_instruction),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(R.string.mission_instruction_1),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
        )
        Text(
            text = stringResource(R.string.mission_instruction_2),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
        )
        Text(
            text = stringResource(R.string.mission_instruction_3),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
        )
        Text(
            text = stringResource(R.string.mission_instruction_4),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
        )
        Text(
            text = stringResource(R.string.mission_instruction_5),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitMissionInstructionPreview() {
    ClasticTheme {
        SubmitMissionInstructions()
    }
}