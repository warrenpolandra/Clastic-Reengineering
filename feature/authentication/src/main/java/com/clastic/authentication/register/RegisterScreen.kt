package com.clastic.authentication.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.authentication.R
import com.clastic.ui.authentication.AuthenticationButton
import com.clastic.ui.authentication.AuthenticationMethodDivider
import com.clastic.ui.authentication.EmailTextField
import com.clastic.ui.authentication.GoogleSignInButton
import com.clastic.ui.authentication.NameTextField
import com.clastic.ui.authentication.PasswordTextField
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {

    val isLoading = false

    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.clastic_logo_text),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GoogleSignInButton(
                isEnabled = !isLoading,
                onClick = { /*TODO*/ },
                stringId = R.string.google_register,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth()
            )
            AuthenticationMethodDivider()
            NameTextField(
                name = nameInput,
                isEnabled = !isLoading,
                onInputChanged = { newValue -> nameInput = newValue},
                modifier = Modifier
                    .fillMaxWidth()
            )
            EmailTextField(
                email = emailInput,
                isEnabled = !isLoading,
                onInputChanged = { newValue -> emailInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
            PasswordTextField(
                password = passInput,
                isEnabled = !isLoading,
                placeholderId = R.string.enter_password,
                onInputChanged = { newValue -> passInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            AuthenticationButton(
                stringId = R.string.register,
                isEnabled = !isLoading,
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = stringResource(R.string.already_have_account),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = AnnotatedString(stringResource(R.string.log_in_to_account)),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = CyanTextField,
                modifier = Modifier.clickable { navigateToLogin() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ClasticTheme {
        RegisterScreen(
            navigateToHome = {},
            navigateToLogin = {}
        )
    }
}