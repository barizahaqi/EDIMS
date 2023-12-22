package com.bangkit.edims.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: ProductRepository) : ViewModel() {

    fun saveNotificationSettings(status: Boolean) {
        viewModelScope.launch {
            repository.saveNotificationSettings(status)
        }
    }

    fun getNotificationSettings() : Flow<Boolean> {
        return repository.getNotificationSettings()
    }
}