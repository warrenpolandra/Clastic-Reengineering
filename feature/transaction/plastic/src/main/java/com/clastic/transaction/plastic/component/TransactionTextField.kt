package com.clastic.transaction.plastic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.transaction.plastic.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
internal fun TransactionTextField(
    text: String,
    labelId: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(labelId),
            color = CyanTextField,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(start = 10.dp)
        )
        OutlinedTextField(
            value = text,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            textStyle = TextStyle(
                color = Color.Gray,
                fontSize = 20.sp
            ),
            onValueChange = {},
            maxLines = 4,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionTextFieldPreview() {
    ClasticTheme {
        TransactionTextField(
            text = "Fill here",
            labelId = R.string.user_id
        )
    }
}