package com.clastic.authentication.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.authentication.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanTextField

@Composable
fun PasswordTextField(
    password: String,
    isEnabled: Boolean,
    placeholderId: Int,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager =  LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    val isPasswordLengthCorrect = remember(password) { password.length >= 6 }
    val isEmptyPassword = remember(password) { password.isBlank() }
    //val isValidPassword = remember(password) { password }
    val isError = !isEmptyPassword && !isPasswordLengthCorrect

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.password),
            color = CyanTextField,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(start = 10.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = onInputChanged,
            enabled = isEnabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
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
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            placeholder = {
                Text(
                    stringResource(placeholderId),
                    color = Color.Gray
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isError,
            trailingIcon = {
                val visibleIcon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                ) {
                    Icon(
                        imageVector = visibleIcon,
                        tint = CyanPrimary,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (!isEmptyPassword && !isPasswordLengthCorrect) {
            Text(
                text = stringResource(R.string.invalid_password_length),
                color = Color.Red,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    ClasticTheme {
        PasswordTextField(
            password = "invalid input",
            isEnabled = true,
            placeholderId = R.string.enter_password,
            onInputChanged = {},
        )
    }
}