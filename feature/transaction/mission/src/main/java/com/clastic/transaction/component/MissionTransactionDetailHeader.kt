package com.clastic.transaction.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.CyanPrimary

@Composable
internal fun MissionTransactionDetailHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.mission_finished),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Icon(
            imageVector = Icons.Default.Verified,
            tint = CyanPrimary,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 40.dp)
                .size(150.dp)
        )
    }
}