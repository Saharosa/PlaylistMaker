package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    override fun onCreate() {
        val sharePref = getSharedPreferences(PLAY_LIST_MAKER, MODE_PRIVATE)
        val theme = sharePref.getString(THEME_KEY,"false").toBoolean()
        if(theme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
    }

}