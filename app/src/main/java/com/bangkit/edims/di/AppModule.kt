package com.bangkit.edims.di

import androidx.room.Room
import com.bangkit.edims.data.retrofit.ApiConfig
import com.bangkit.edims.database.ProductDatabase
import com.bangkit.edims.database.ProductRepository
import com.bangkit.edims.database.ProductRepositoryImpl
import com.bangkit.edims.database.SettingPreference
import com.bangkit.edims.database.UserPreference
import com.bangkit.edims.presentation.ui.add.AddViewModel
import com.bangkit.edims.presentation.ui.detail.DetailViewModel
import com.bangkit.edims.presentation.ui.home.HomeViewModel
import com.bangkit.edims.presentation.ui.login.LoginViewModel
import com.bangkit.edims.presentation.ui.profile.ProfileViewModel
import com.bangkit.edims.presentation.ui.settings.SettingsViewModel
import com.bangkit.edims.presentation.ui.signup.SignUpViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }

    single {
        get<ProductRepository>().getToken()
    }

    single {
        SettingPreference(androidContext())
    }

    single {
        UserPreference(androidContext())
    }

    single {
        ApiConfig.getApiService(get())
    }

    single {
        get<ProductDatabase>().productDao()
    }

    single<ProductRepository> {
        ProductRepositoryImpl(get(), get(), get(), get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        AddViewModel(get())
    }

    viewModel {
        DetailViewModel(get())
    }

    viewModel {
        SettingsViewModel(get())
    }

    viewModel {
        ProfileViewModel(get())
    }

    viewModel{
        LoginViewModel(get())
    }

    viewModel{
        SignUpViewModel(get())
    }
}