package com.example.playlistmaker.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.domain.api.sharedPrefsRepository
import com.example.playlistmaker.presentation.ui.settings.THEME_KEY

class sharedPrefsRepositoryImpl(val sharePref: SharedPreferences): sharedPrefsRepository {
    override fun getString(key: String, defValue: String):String?{
        return sharePref.getString(key,defValue)
    }
}