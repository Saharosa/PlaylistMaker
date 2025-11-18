package com.example.playlistmaker.domain.api

import android.content.SharedPreferences

interface sharedPrefsRepository {
    fun getString(key:String,defValue:String):String?
}