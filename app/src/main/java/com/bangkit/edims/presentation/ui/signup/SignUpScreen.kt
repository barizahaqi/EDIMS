package com.bangkit.edims.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.Validation
import com.bangkit.edims.data.Result
import com.bangkit.edims.presentation.components.button.CustomContainedButton
import com.bangkit.edims.presentation.components.loading.ShowLoading
import com.bangkit.edims.presentation.components.text.CustomTextField
import com.bangkit.edims.presentation.theme.PaleLeaf

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    navigateToLogin: () -> Unit,
) {
    val result by viewModel.result.collectAsState()

    var showLoading by remember {
        mutableStateOf(false)
    }

    if (showLoading) {
        ShowLoading(
            modifier = Modifier
                .zIndex(2f)
                .background(Color.Gray.copy(alpha = 0.2f))
        )
    }

    LaunchedEffect(result) {
        when (result) {
            Result.Loading -> {}

            is Result.Success -> {
                val signupResult = (result as Result.Success).data
                showSnackbar(signupResult.message, SnackbarDuration.Short)
                showLoading = false
                navigateToLogin()
            }

            is Result.Error -> {
                val errorMessage = (result as Result.Error).errorMessage
                showSnackbar(errorMessage, SnackbarDuration.Short)
                showLoading = false
                viewModel.resetResult()
            }
        }
    }


    var username by remember { mutableStateOf("") }
    var usernameValid by remember { mutableStateOf(true) }

    var email by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }

    var password by remember { mutableStateOf("") }
    var passwordValid by remember { mutableStateOf(true) }

    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordValid by remember { mutableStateOf(true) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            painterResource(R.drawable.signup_logo),
            "Logo Sign Up",
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            placeholder = "Username",
            text = username,
            errorMessage = stringResource(R.string.username_validation),
            onValueChange = {
                username = it
                usernameValid = Validation.validateUsername(username)
            },
            isError = !usernameValid
        )
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
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ) {
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
        CustomTextField(placeholder = "Confirm Password",
            text = confirmPassword,
            errorMessage = stringResource(R.string.confirm_password_validation),
            onValueChange = {
                confirmPassword = it
                confirmPasswordValid = confirmPassword == password
            },
            isError = !confirmPasswordValid,
            isVisible = isConfirmPasswordVisible,
            trailingIcon = {
                IconButton(
                    onClick = {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible
                    }
                ) {
                    if (isConfirmPasswordVisible) {
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
            isEnabled = usernameValid && emailValid && passwordValid && confirmPasswordValid && (username != "") && (email != "") && (password != "") && (confirmPassword != ""),
            text = "Sign Up",
            onClick = {
                viewModel.signUp(username, email, password)
                showLoading = true
            })
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Already have an account? ", style = MaterialTheme.typography.bodyLarge)
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
                text = AnnotatedString("Login"),
                onClick = { navigateToLogin() },
                style = MaterialTheme.typography.bodyLarge.copy(color = PaleLeaf)
            )
        }
    }
}
