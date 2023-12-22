package com.bangkit.edims.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val NOTIFICATION_SETTINGS = booleanPreferencesKey("notification_settings")
    }

    val getNotificationSettings: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_SETTINGS] ?: false
        }

    suspend fun saveNotificationSettings(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_SETTINGS] = status
        }
    }
}