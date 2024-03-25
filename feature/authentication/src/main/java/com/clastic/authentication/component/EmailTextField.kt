package com.clastic.authentication.component

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.authentication.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
fun EmailTextField(
    email: String,
    isEnabled: Boolean,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val isEmailEmpty = remember(email) { email.isBlank() }
    val isEmailValid = remember(email) { Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    val isError = !isEmailValid && !isEmailEmpty

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.email),
            color = CyanTextField,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(start = 10.dp)
        )
        OutlinedTextField(
            value = email,
            enabled = isEnabled,
            onValueChange = onInputChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = CyanTextField
                )
            },
            shape = RoundedCornerShape(19.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = CyanTextField,
                unfocusedBorderColor = CyanTextField,
                errorBorderColor = Color.Red
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            placeholder = {
                Text(
                    stringResource(R.string.enter_email_here),
                    color = Color.Gray
                )
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
        )

        if (isError) {
            Text(
                text = stringResource(R.string.invalid_email),
                color = Color.Red,
                style = MaterialTheme.typography.caption,
                modifier = modifier.padding(start = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview() {
    ClasticTheme {
        EmailTextField(
            email = "invalid input",
            onInputChanged = {},
            isEnabled = true,
        )
    }
}