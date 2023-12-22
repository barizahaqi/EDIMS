package com.bangkit.edims.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.bangkit.edims.R
import com.bangkit.edims.presentation.theme.PaleLeaf
import com.bangkit.edims.presentation.theme.Sulu
import com.bangkit.edims.presentation.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateNext: () -> Unit
) {
    val gradiantBackground = Brush.horizontalGradient(
        0.0f to PaleLeaf,
        1.0f to Sulu,
        startX = 0.0f,
        endX = 1000.0f
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradiantBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.displayLarge, color = White)
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navigateNext()
    }
}