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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.bangkit.edims.presentation.components.button.CustomContainedButton
import com.bangkit.edims.presentation.components.text.CustomTextField
import com.bangkit.edims.presentation.theme.PaleLeaf

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = LoginViewModel(),
) {
    val message by viewModel.message.observeAsState()

    var email by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }

    var password by remember { mutableStateOf("") }
    var passwordValid by remember { mutableStateOf(true) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
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
        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message!!, style = MaterialTheme.typography.bodyLarge)
        }
        CustomContainedButton(text = "Login", onClick = {
            viewModel.login(email, password)
        })
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Don't have an account? ", style = MaterialTheme.typography.bodyLarge)
            ClickableText(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - size.height/3
                    drawLine(
                        color = PaleLeaf,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                text = AnnotatedString("Sign Up"),
                onClick = {},
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary)
            )
        }
    }
}