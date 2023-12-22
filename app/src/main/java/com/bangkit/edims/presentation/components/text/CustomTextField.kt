package com.bangkit.edims.presentation.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bangkit.edims.presentation.theme.White



@Composable
fun CustomTextField(
    placeholder: String,
    text: String,
    errorMessage: String?,
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    isVisible: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLine: Int = 1,
) {
    Column {
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            maxLines = maxLine,
            singleLine = singleLine,
            interactionSource = remember { MutableInteractionSource() },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 3.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
                        )
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                    } else {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(vertical = 12.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            innerTextField()
                        }
                    }
                    if (trailingIcon != null) {
                        trailingIcon()
                    } else {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            },
        )
        Text(
            text = if (isError) errorMessage!! else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}