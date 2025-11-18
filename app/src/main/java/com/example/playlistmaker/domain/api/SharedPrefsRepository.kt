package com.example.playlistmaker.domain.api

interface SharedPrefsRepository {
    fun getString(key:String,defValue:String):String?
    fun edit(term:String)
}