package com.clastic.authentication.login

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.authentication.R
import com.clastic.ui.authentication.AuthenticationButton
import com.clastic.ui.authentication.AuthenticationMethodDivider
import com.clastic.ui.authentication.EmailTextField
import com.clastic.ui.authentication.GoogleSignInButton
import com.clastic.ui.authentication.PasswordTextField
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<LoginViewModel>()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
           painter = painterResource(R.drawable.clastic_logo_text),
           contentDescription = null,
           modifier = Modifier
               .padding(bottom = 12.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GoogleSignInButton(
                isEnabled = true,
                onClick = { /*TODO*/ },
                stringId = R.string.google_sign_in,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth()
            )
            AuthenticationMethodDivider()
            EmailTextField(
                email = "EmailInput",
                isEnabled = true,
                onInputChanged = { newValue -> },
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .fillMaxWidth()
            )
            PasswordTextField(
                password = "Password",
                isEnabled = true,
                placeholderId = R.string.enter_password,
                onInputChanged = { newValue -> },
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth()
            )
            AuthenticationButton(
                stringId = R.string.login,
                isEnabled = true,
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = stringResource(R.string.dont_have_account),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = AnnotatedString(stringResource(R.string.register_now)),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = CyanTextField,
                modifier = Modifier.clickable { navigateToRegister() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ClasticTheme {
        LoginScreen(
            navigateToRegister = {},
            navigateToHome = {}
        )
    }
}