package com.example.playlistmaker.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.presentation.ui.settings.PLAY_LIST_MAKER
import com.example.playlistmaker.presentation.ui.settings.THEME_KEY

class App : Application() {

    override fun onCreate() {
        val sharePref = getSharedPreferences(PLAY_LIST_MAKER, MODE_PRIVATE)
        val theme = sharePref.getString(THEME_KEY,"false").toBoolean()
        if(theme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
    }

}