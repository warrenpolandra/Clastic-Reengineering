package com.clastic.transaction.plastic.component

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.transaction.plastic.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
internal fun PlasticWeightTextField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var plasticWeight by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = plasticWeight,
        onValueChange = { newWeight ->
            if (newWeight.length <= 3) {
                plasticWeight = newWeight
                onValueChange(newWeight)
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.weight_placeholder),
                color = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = CyanTextField,
            unfocusedBorderColor = CyanTextField,
            trailingIconColor = Color.Black,
            placeholderColor = Color.Gray,
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            Text(
                text = stringResource(R.string.kg),
                color = Color.Black
            )
        },
        modifier = modifier.width(100.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PlasticWeightTextFieldPreview() {
    ClasticTheme {
        PlasticWeightTextField(onValueChange = {})
    }
}