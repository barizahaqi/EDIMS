package com.bangkit.edims.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.edims.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference (private val context : Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")
        val USER_ID_DATA = intPreferencesKey("user_id_data")
        val USERNAME_DATA = stringPreferencesKey("username_data")
        val EMAIL_DATA = stringPreferencesKey("email_data")
        val IMAGE_PROFILE_DATA = stringPreferencesKey("image_profile_data")
        val TOKEN_KEY = stringPreferencesKey("token_key")
    }

    suspend fun saveLoginData(user : User) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_DATA] = user.userId ?: 0
            preferences[USERNAME_DATA] = user.username ?: ""
            preferences[EMAIL_DATA] = user.email ?: ""
            preferences[IMAGE_PROFILE_DATA] = user.imageProfile ?: ""
            preferences[TOKEN_KEY] = user.token ?: ""
        }
    }

    suspend fun clearLoginData() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_DATA)
            preferences.remove(USERNAME_DATA)
            preferences.remove(EMAIL_DATA)
            preferences.remove(IMAGE_PROFILE_DATA)
            preferences.remove(TOKEN_KEY)
        }
    }

    val getUserData: Flow<User> = context.dataStore.data
        .map { preferences ->
            User(
                userId = preferences[USER_ID_DATA],
                username = preferences[USERNAME_DATA],
                email = preferences[EMAIL_DATA],
                imageProfile = preferences[IMAGE_PROFILE_DATA],
                token = preferences[TOKEN_KEY]
            )
        }

    val token: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY] ?: ""
    }

    fun isTokenAvailable(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences.contains(TOKEN_KEY)
        }
    }

    suspend fun changeImageProfile (imageProfile: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(IMAGE_PROFILE_DATA)
            preferences[IMAGE_PROFILE_DATA] = imageProfile
        }
    }
}