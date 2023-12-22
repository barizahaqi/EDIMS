package com.bangkit.edims.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bangkit.edims.R
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.User
import com.bangkit.edims.presentation.components.shimmer.AnimatedShimmer
import com.bangkit.edims.presentation.theme.Beige
import com.bangkit.edims.presentation.theme.PaleLeaf
import com.bangkit.edims.presentation.theme.Tacao

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    navigateToLogOut: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val result by profileViewModel.result.collectAsState()

    when (result) {
        Result.Loading -> {
            profileViewModel.getUserData()
        }

        is Result.Success -> {
            val userData = (result as Result.Success).data

            ProfileScreenContent(
                modifier = modifier,
                userData = userData,
                navigateToLogOut = navigateToLogOut,
                navigateToSettings = navigateToSettings
            )
        }

        is Result.Error -> {
            val errorMessage = (result as Result.Error).errorMessage
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    userData: User,
    navigateToLogOut: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val profileUsername = userData.username
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Beige)
    ) {
        ProfileBar(
            username = profileUsername
        )
        ProfileContent(
            navigateToLogOut = navigateToLogOut,
            navigateToSettings = navigateToSettings
        )
    }
}

@Composable
private fun ProfileBar(
    modifier: Modifier = Modifier,
    username: String?
) {
    var showShimmer by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
            .background(PaleLeaf)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.profile_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .padding(16.dp)
            )
            Box(
                modifier = Modifier
                    .size(110.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Tacao)
                        .align(Alignment.Center)
                        .background(AnimatedShimmer(showShimmer = showShimmer)),
                    contentScale = ContentScale.Crop,
                )
            }
            Text(
                text = username ?: "Guest",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    navigateToLogOut: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val settingsItems = listOf(
        R.string.profile_settings_items,
        R.string.profile_logout_items,
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Beige),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            settingsItems.map {
                ProfileItem(
                    name = it,
                    onSettingItemClick = { name ->
                        when (name) {
                            R.string.profile_settings_items -> navigateToSettings()
                            R.string.profile_logout_items -> showDialog = true
                        }
                    })
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
    if (showDialog) {
        LogoutDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                navigateToLogOut()
                showDialog = false
            }
        )
    }
}

@Composable
fun ProfileItem(
    name: Int,
    onSettingItemClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSettingItemClick(name) },
    ) {
        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Log out") },
        text = { Text(text = "Are you sure you want to logout?") },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Yes", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "No", color = Color.Black)
            }
        }

    )
}