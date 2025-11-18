package com.example.playlistmaker.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.ID.appModule
import com.example.playlistmaker.ID.interactorModule
import com.example.playlistmaker.ID.networkModule
import com.example.playlistmaker.ID.repositoryModule
import com.example.playlistmaker.ID.viewModelModule
import com.example.playlistmaker.domain.api.SharedPrefsInteractor
import com.example.playlistmaker.presentation.ui.settings.THEME_KEY
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                interactorModule,
                viewModelModule
            )
        }
        val sharePrefInteractor : SharedPrefsInteractor by inject()
        val theme = sharePrefInteractor.getString(THEME_KEY,"false").toBoolean()
        if(theme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
    }

}