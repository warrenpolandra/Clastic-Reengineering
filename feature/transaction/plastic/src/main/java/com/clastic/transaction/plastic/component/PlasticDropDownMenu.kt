package com.clastic.transaction.plastic.component

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.transaction.plastic.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
fun PlasticDropDownMenu(
    plasticTypeList: List<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedPlasticType by rememberSaveable { mutableStateOf("") }

    PlasticDropDownMenuContent(
        isExpanded = isExpanded,
        onExpandedChanged = { isExpanded = it },
        selectedPlasticType = selectedPlasticType,
        plasticTypeList = plasticTypeList,
        onPlasticTypeSelected = { plasticType ->
            selectedPlasticType = plasticType
            onValueChanged(plasticType)
            isExpanded = false
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlasticDropDownMenuContent(
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    selectedPlasticType: String,
    plasticTypeList: List<String>,
    onPlasticTypeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpandedChanged,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedPlasticType,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(text = stringResource(R.string.type)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = CyanTextField,
                unfocusedBorderColor = CyanTextField,
                trailingIconColor = CyanTextField,
                focusedTrailingIconColor = CyanTextField,
                placeholderColor = Color.Gray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(120.dp)
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChanged(false) }
        ) {
            plasticTypeList.map { plasticType ->
                DropdownMenuItem(
                    onClick = { onPlasticTypeSelected(plasticType) }
                ) { Text(text = plasticType) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlasticDropDownMenuPreview() {
    ClasticTheme {
        PlasticDropDownMenuContent(
            isExpanded = true,
            onExpandedChanged = {},
            selectedPlasticType = "PET",
            plasticTypeList = emptyList(),
            onPlasticTypeSelected = {}
        )
    }
}