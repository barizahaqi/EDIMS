package com.bangkit.edims.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.Validation
import com.bangkit.edims.data.Result
import com.bangkit.edims.presentation.components.button.CustomContainedButton
import com.bangkit.edims.presentation.components.loading.ShowLoading
import com.bangkit.edims.presentation.components.text.CustomTextField
import com.bangkit.edims.presentation.theme.PaleLeaf

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    navigateToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val result by viewModel.result.collectAsState()

    var showLoading by remember {
        mutableStateOf(false)
    }

    if (showLoading) {
        ShowLoading()
    }

    LaunchedEffect(result) {
        when (result) {
            Result.Loading -> {}

            is Result.Success -> {
                val loginResult = (result as Result.Success).data
                val resultMessage = loginResult.message
                showSnackbar(resultMessage, SnackbarDuration.Short)
                showLoading = false

                val dataUser = loginResult.user
                val userId = dataUser.id
                val username = dataUser.username
                val email = dataUser.email
                val token = loginResult.token
                viewModel.saveLoginData(userId, username, email, token)
                onLoginSuccess()
            }

            is Result.Error -> {
                val errorMessage = (result as Result.Error).errorMessage
                showSnackbar(errorMessage, SnackbarDuration.Short)
                showLoading = false
                viewModel.resetResult()
            }

        }
    }

    var email by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }

    var password by remember { mutableStateOf("") }
    var passwordValid by remember { mutableStateOf(true) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            painterResource(R.drawable.login_logo),
            "Logo Login",
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            placeholder = "Email",
            text = email,
            errorMessage = stringResource(R.string.email_validation),
            onValueChange = {
                email = it
                emailValid = Validation.validateEmail(email)
            },
            isError = !emailValid
        )
        CustomTextField(placeholder = "Password",
            text = password,
            errorMessage = stringResource(R.string.password_validation),
            onValueChange = {
                password = it
                passwordValid = Validation.validatePassword(password)
            },
            isError = !passwordValid,
            isVisible = isPasswordVisible,
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    if (isPasswordVisible) {
                        Image(
                            painterResource(R.drawable.ic_visible),
                            "Password Visibility Toggle",
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Image(
                            painterResource(R.drawable.ic_non_visible),
                            "Password Visibility Toggle",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            })
        CustomContainedButton(
            isEnabled = emailValid && passwordValid && (email != "") && (password != ""),
            text = "Login",
            onClick = {
                viewModel.loginUser(email, password)
                showLoading = true
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Don't have an account? ", style = MaterialTheme.typography.bodyLarge)
            ClickableText(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - size.height / 3
                    drawLine(
                        color = PaleLeaf,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                text = AnnotatedString("Sign Up"),
                onClick = {
                    navigateToSignUp()
                },
                style = MaterialTheme.typography.bodyLarge.copy(color = PaleLeaf)
            )
        }
    }
}