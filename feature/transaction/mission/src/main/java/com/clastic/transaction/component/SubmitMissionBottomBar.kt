package com.clastic.transaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.ui.theme.CyanPrimaryVariant2

@Composable
internal fun SubmitMissionBottomBar(
    buttonEnabled: Boolean,
    isTnCChecked: Boolean,
    onTnCChange: () -> Unit,
    onSubmitClick: () -> Unit,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = onTnCChange) {
                    Icon(
                        imageVector = if (isTnCChecked)
                            Icons.Filled.CheckCircle
                        else
                            Icons.Outlined.Circle,
                        tint = CyanPrimary,
                        contentDescription = "checkbox"
                    )
                }
                Text(
                    text = stringResource(R.string.agree_terms_and_condition),
                    style = MaterialTheme.typography.subtitle1.copy(
                        CyanPrimaryVariant2,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                enabled = buttonEnabled,
                onClick = onSubmitClick,
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

@Preview
@Composable
private fun SubmitMissionBottomBarPreview() {
    ClasticTheme {
        SubmitMissionBottomBar(
            buttonEnabled = true,
            isTnCChecked = true,
            onSubmitClick = {},
            onTnCChange = {}
        )
    }
}