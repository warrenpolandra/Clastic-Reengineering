package com.clastic.transaction.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
internal fun MissionTransactionDetailItem(
    fieldName: String,
    fieldValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = fieldName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = CyanPrimary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = fieldValue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = CyanPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MissionTransactionDetailItemPreview() {
    ClasticTheme {
        MissionTransactionDetailItem(
            fieldName = "nama",
            fieldValue = "mission1"
        )
    }
}