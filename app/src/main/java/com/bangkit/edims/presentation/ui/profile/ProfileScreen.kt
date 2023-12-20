package com.bangkit.edims.presentation.ui.profile

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.edims.R
import com.bangkit.edims.data.User
import com.bangkit.edims.presentation.theme.Beige
import com.bangkit.edims.presentation.theme.PaleLeaf
import com.bangkit.edims.presentation.theme.Tacao

@Composable
fun ProfileScreen(
    navigateToHelpCenter: () -> Unit,
    navigateToLogOut: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val user = User(null, null, null, null)
    ProfileScreenContent(
        userData = user,
        navigateToHelpCenter = navigateToHelpCenter,
        navigateToLogOut = navigateToLogOut,
        navigateToSettings = navigateToSettings
    )
}

@Composable
fun ProfileScreenContent(
    userData: User,
    navigateToHelpCenter: () -> Unit,
    navigateToLogOut: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val profileIcon = userData.profileIcon
    val profileUsername = userData.username
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige)
    ) {
        ProfileBar(imageProfile = profileIcon, username = profileUsername)
        ProfileContent(
            navigateToHelpCenter = navigateToHelpCenter,
            navigateToLogOut = navigateToLogOut,
            navigateToSettings = navigateToSettings
        )
    }
}

@Composable
private fun ProfileBar(
    modifier: Modifier = Modifier,
    imageProfile: String?,
    username: String?
) {
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
                text = "Profile",
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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageProfile)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile Image",
                    placeholder = painterResource(R.drawable.ic_user),
                    error = painterResource(R.drawable.ic_user),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Tacao)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
                FloatingActionButton(
                    onClick = {
                        /*TODO*/
                    },
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = Beige
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .size(20.dp),
                        tint = Color.Black
                    )
                }
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
    navigateToHelpCenter: () -> Unit,
    navigateToLogOut: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val settingsItems = listOf(
        "Settings",
        "Help Center",
        "Logout",
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
                            "Settings" -> navigateToSettings()
                            "Help Center" -> navigateToHelpCenter()
                            "Logout" -> navigateToLogOut()
                        }
                    })
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ProfileItem(
    name: String,
    onSettingItemClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSettingItemClick(name) },
    ) {
        Text(
            text = name,
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