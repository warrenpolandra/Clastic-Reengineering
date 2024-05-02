package com.clastic.transaction.plastic.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.transaction.plastic.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanTextField
import com.clastic.utils.NumberUtil

@Composable
internal fun PlasticTransactionHistoryCard(
    date: String,
    dropPointId: String,
    points: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<PlasticTransactionHistoryCardViewModel>()
    val dropPointName by viewModel.dropPointName.collectAsState()

    LaunchedEffect(true) { viewModel.fetchDropPointNameById(dropPointId) }

    PlasticTransactionHistoryCardContent(
        date = date,
        location = dropPointName,
        points = points,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
private fun PlasticTransactionHistoryCardContent(
    date: String,
    location: String,
    points: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(3.dp, CyanTextField),
        backgroundColor = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = date,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.history_location, location),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.added_points, NumberUtil.formatNumberToGrouped(points)),
                    fontWeight = FontWeight.Bold,
                    color = CyanPrimary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.detail),
                    color = CyanPrimary,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = CyanPrimary,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PlasticTransactionHistoryCardPreview() {
    ClasticTheme {
        PlasticTransactionHistoryCardContent(
            date = "Senin 12 Agustus 2023 jam 18:00:29",
            location = "RPTRA Dahlia",
            points = 13000,
            onClick = {}
        )
    }
}