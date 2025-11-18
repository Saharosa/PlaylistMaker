package com.example.playlistmaker.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.domain.api.SharedPrefsRepository
import com.example.playlistmaker.presentation.ui.settings.THEME_KEY

class sharedPrefsRepositoryImpl(val sharePref: SharedPreferences): SharedPrefsRepository {
    override fun getString(key: String, defValue: String):String?{
        return sharePref.getString(key,defValue)
    }
    override fun edit(term:String){
        sharePref.edit().putString(THEME_KEY,term).apply()
    }
}