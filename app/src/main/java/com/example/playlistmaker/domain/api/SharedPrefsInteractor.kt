package com.example.playlistmaker.domain.api

interface SharedPrefsInteractor {
    fun getString(key:String,defValue:String):String?
    fun edit(term:String)
}