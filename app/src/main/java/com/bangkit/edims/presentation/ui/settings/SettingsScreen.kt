package com.bangkit.edims.presentation.ui.settings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.Permission
import com.bangkit.edims.notification.NotificationWorker
import com.bangkit.edims.presentation.theme.Beige
import com.bangkit.edims.presentation.theme.PaleLeaf
import com.bangkit.edims.presentation.theme.Tacao

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    context : Context,
    settingsViewModel: SettingsViewModel,
    navigateBack: () -> Unit
) {
    val notificationSettings by settingsViewModel.getNotificationSettings().collectAsState(initial = false)

    LaunchedEffect(notificationSettings) {
        val notif = NotificationWorker()
        if (notificationSettings) {
            notif.setDailyReminder(context)
        } else {
            notif.cancelAlarm(context)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Permission(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            rationale = "Please grant the permission.",
            permissionNotAvailableContent = {
                Column(modifier) {
                    Text("No notifications")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        })
                    }) {
                        Text("Open Settings")
                    }
                }
            }
        )
    }

    SettingsContent(
        modifier = modifier,
        saveNotificationSettings = settingsViewModel::saveNotificationSettings,
        notificationSettings = notificationSettings,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    saveNotificationSettings: (Boolean) -> Unit,
    notificationSettings: Boolean,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                navigateBack()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PaleLeaf)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Beige)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            NotificationSettings(
                saveNotificationSettings = saveNotificationSettings,
                notificationSettings = notificationSettings
            )
        }
    }
}

@Composable
private fun NotificationSettings(
    modifier: Modifier = Modifier,
    saveNotificationSettings: (Boolean) -> Unit,
    notificationSettings: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.settings_turn_on_notif),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = notificationSettings,
                onCheckedChange = {
                    saveNotificationSettings(!notificationSettings)
                },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = Color.Transparent,
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Tacao,
                    uncheckedBorderColor = Tacao,
                    uncheckedThumbColor = Tacao,
                    uncheckedTrackColor = Color.White
                )
            )
        }
    }
}