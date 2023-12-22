package com.bangkit.edims.presentation.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bangkit.edims.presentation.theme.Black
import com.bangkit.edims.presentation.theme.Tacao

@Composable
fun CustomContainedButton(
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    text: String,
    color: Color = Tacao,
    textColor: Color = Black,
    height: Int = 52,
    borderRadius: Int = 8,
    elevation: Int = 2,
) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
        )
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(borderRadius.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = textColor,
                disabledContainerColor = color.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp),
            enabled = isEnabled,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = elevation.dp,
                pressedElevation = elevation.dp,
                disabledElevation = 0.dp,
            ),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}