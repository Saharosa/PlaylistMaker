package com.example.playlistmaker.domain.api

interface sharedPrefsInteractor {
    fun getString(key:String,defValue:String):String?
}