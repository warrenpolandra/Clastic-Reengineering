package com.clastic.transaction.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.utils.NumberUtil
import com.clastic.utils.TimeUtil
import com.google.firebase.Timestamp

@Composable
internal fun MissionTransactionDetailInfo(
    missionTransactionId: String,
    missionReward: Int,
    missionTransactionTime: Timestamp,
    submissionUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.mission_detail),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        MissionHistoryDetailItem(
            fieldName = stringResource(R.string.transaction_code),
            fieldValue = missionTransactionId
        )
        MissionHistoryDetailItem(
            fieldName = stringResource(R.string.mission_points_total),
            fieldValue = stringResource(R.string.points_value, NumberUtil.formatNumberToGrouped(missionReward))
        )
        MissionHistoryDetailItem(
            fieldName = stringResource(R.string.time),
            fieldValue = TimeUtil.timestampToStringFormat(missionTransactionTime)
        )
        Divider(
            color = CyanPrimaryVariant,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .height(2.dp)
        )
        Text(
            text = stringResource(R.string.mission_submission_proof),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .border(4.dp, CyanPrimary, RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                model = submissionUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
        }
    }
}