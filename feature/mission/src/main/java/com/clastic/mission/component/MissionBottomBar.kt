package com.clastic.mission.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clastic.mission.R
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.ui.theme.CyanPrimaryVariant2

@Composable
fun MissionBottomBar(
    onJoinButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            Text(
                text = stringResource(R.string.terms_and_condition),
                style = MaterialTheme.typography.subtitle1.copy(CyanPrimaryVariant2),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { onJoinButtonClick() },
                modifier = Modifier.clip(RoundedCornerShape(60.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = CyanPrimaryVariant
                )
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    style = MaterialTheme.typography.subtitle1.copy(Color.White),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}