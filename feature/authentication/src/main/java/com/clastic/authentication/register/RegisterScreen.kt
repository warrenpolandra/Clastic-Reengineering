package com.clastic.authentication.register

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
import com.clastic.authentication.component.NameTextField
import com.clastic.authentication.component.PasswordTextField
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>()
    val nameInput by viewModel.nameInput.collectAsState()
    val emailInput by viewModel.emailInput.collectAsState()
    val passInput by viewModel.passInput.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                viewModel.getGoogleRegisterResultFromIntent(
                    intent = result.data ?: return@rememberLauncherForActivityResult,
                    onSignInSuccess = {
                        showToast(context, context.getString(R.string.register_success))
                        viewModel.resetAuthState()
                        navigateToHome()
                    },
                    onSignInFailed = { error ->
                        showToast(context, context.getString(R.string.register_error, error.toString()))
                    }
                )
            }
            viewModel.setIsLoading(false)
        }
    )

    RegisterScreenContent(
        nameInput = nameInput,
        emailInput = emailInput,
        passInput = passInput,
        isLoading = isLoading,
        onNameChange = { newName -> viewModel.setNameInput(newName) },
        onEmailChange = { newEmail -> viewModel.setEmailInput(newEmail) },
        onPassChange = { newPass -> viewModel.setPasswordInput(newPass) },
        onGoogleRegisterClick = {
            viewModel.registerWithGoogle { intentSender ->
                launcher.launch(
                    IntentSenderRequest.Builder(
                        intentSender ?: return@registerWithGoogle
                    ).build()
                )
            }
        },
        onEmailRegisterClick = {
            viewModel.registerWithEmail(
                onResultSuccess = {
                    showToast(context, context.getString(R.string.register_success))
                    viewModel.resetAuthState()
                    navigateToHome()
                },
                onResultFailed = { error ->
                    showToast(context, context.getString(R.string.register_error, error))
                }
            )
        },
        navigateToLogin = navigateToLogin,
        modifier = modifier
    )
}

@Composable
private fun RegisterScreenContent(
    nameInput: String,
    emailInput: String,
    passInput: String,
    isLoading: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onGoogleRegisterClick: () -> Unit,
    onEmailRegisterClick: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
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
                    onClick = onGoogleRegisterClick,
                    stringId = R.string.google_register,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                )
                AuthenticationMethodDivider()
                NameTextField(
                    name = nameInput,
                    isEnabled = !isLoading,
                    onInputChanged = onNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                EmailTextField(
                    email = emailInput,
                    isEnabled = !isLoading,
                    onInputChanged = onEmailChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                )
                PasswordTextField(
                    password = passInput,
                    isEnabled = !isLoading,
                    placeholderId = R.string.enter_password,
                    onInputChanged = onPassChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
                AuthenticationButton(
                    stringId = R.string.register,
                    isEnabled = !isLoading,
                    onClick = onEmailRegisterClick,
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
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    ClasticTheme {
        RegisterScreenContent(
            nameInput = "Warren",
            emailInput = "warren@gmail.com",
            passInput = "12345",
            isLoading = false,
            onNameChange = {},
            onEmailChange = {},
            onPassChange = {},
            onGoogleRegisterClick = {},
            onEmailRegisterClick = {},
            navigateToLogin = {}
        )
    }
}