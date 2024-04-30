package com.clastic.transaction.plastic.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.transaction.plastic.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField
import com.clastic.utils.NumberUtil

@Composable
internal fun PlasticInput(
    plasticTypeList: List<String>,
    onValueChanged: (weight: Float , points: Int) -> Unit,
    onTypeSelected: (plasticType: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var totalPoints by rememberSaveable {
        mutableIntStateOf(0)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        PlasticDropDownMenu(
            onValueChanged = onTypeSelected,
            plasticTypeList = plasticTypeList,
            modifier = Modifier.padding(end = 10.dp)
        )
        PlasticWeightTextField(
            onValueChange = { newValue ->
                val weightTotal = newValue.toFloatOrNull() ?: 0f
                totalPoints = (weightTotal * 3000).toInt()
                onValueChanged(weightTotal, totalPoints)
            }
        )
        Text(
            text = stringResource(R.string.equals),
            fontSize = 30.sp,
            color = CyanTextField,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = stringResource(
                R.string.total_points,
                NumberUtil.formatNumberToLocale(totalPoints)
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CyanTextField
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlasticInputPreview() {
    ClasticTheme {
        PlasticInput(
            plasticTypeList = emptyList(),
            onValueChanged = { _, _ -> },
            onTypeSelected = {}
        )
    }
}