package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.sharedPrefsInteractor
import com.example.playlistmaker.domain.api.sharedPrefsRepository

class sharedPrefsInteractorImpl(val repository: sharedPrefsRepository): sharedPrefsInteractor {
    override fun getString(key: String, defValue: String):String?{
        return repository.getString(key,defValue)
    }
}