package com.example.playlistmaker.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.presentation.ui.settings.PLAY_LIST_MAKER
import com.example.playlistmaker.presentation.ui.settings.THEME_KEY

class App : Application() {

    override fun onCreate() {
        val sharePrefInteractor = Creator.provideSharedPrefsInteractor(this)
        val theme = sharePrefInteractor.getString(THEME_KEY,"false").toBoolean()
        if(theme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
    }

}