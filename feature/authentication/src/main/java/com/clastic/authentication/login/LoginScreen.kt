package com.clastic.authentication.login

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.authentication.R
import com.clastic.authentication.component.AuthenticationButton
import com.clastic.authentication.component.AuthenticationMethodDivider
import com.clastic.authentication.component.EmailTextField
import com.clastic.authentication.component.GoogleSignInButton
import com.clastic.authentication.component.PasswordTextField
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
    val context = LocalContext.current
    val emailInput by viewModel.emailInput.collectAsState()
    val passInput by viewModel.passInput.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                viewModel.getGoogleSignInResultFromIntent(
                    intent = result.data ?: return@rememberLauncherForActivityResult,
                    onSignInSuccess = {
                        showToast(context, "Login Success!")
                        viewModel.resetAuthState()
                        navigateToHome()
                    },
                    onSignInFailed = { error ->
                        showToast(context, "Login Error: $error")
                    }
                )
            }
            viewModel.setIsLoading(false)
        }
    )

    LoginScreenContent(
        emailInput = emailInput,
        passInput = passInput,
        isLoading = isLoading,
        onEmailChange = { newEmail -> viewModel.setEmailInput(newEmail) },
        onPassChange = { newPass -> viewModel.setPasswordInput(newPass) },
        onGoogleSignInClick = {
            viewModel.loginWithGoogle { intentSender ->
                launcher.launch(
                    IntentSenderRequest.Builder(
                        intentSender ?: return@loginWithGoogle
                    ).build()
                )
            }
        },
        navigateToRegister = navigateToRegister,
        modifier = modifier
    )
}

@Composable
fun LoginScreenContent(
    emailInput: String,
    passInput: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onGoogleSignInClick: () -> Unit,
    navigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
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
                    isEnabled = !isLoading,
                    onClick = onGoogleSignInClick,
                    stringId = R.string.google_sign_in,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                )
                AuthenticationMethodDivider()
                EmailTextField(
                    email = emailInput,
                    isEnabled = !isLoading,
                    onInputChanged = onEmailChange,
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .fillMaxWidth()
                )
                PasswordTextField(
                    password = passInput,
                    isEnabled = !isLoading,
                    placeholderId = R.string.enter_password,
                    onInputChanged = onPassChange,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                )
                AuthenticationButton(
                    stringId = R.string.login,
                    isEnabled = !isLoading,
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
                    modifier = Modifier
                        .clickable(
                            enabled = !isLoading,
                            onClick = navigateToRegister
                        )
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ClasticTheme {
        LoginScreenContent(
            emailInput = "email@gmail.com",
            passInput = "12345",
            isLoading = true,
            onEmailChange = {},
            onPassChange = {},
            onGoogleSignInClick = {},
            navigateToRegister = {}
        )
    }
}