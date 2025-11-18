package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.SharedPrefsInteractor
import com.example.playlistmaker.domain.api.SharedPrefsRepository

class sharedPrefsInteractorImpl(val repository: SharedPrefsRepository): SharedPrefsInteractor {
    override fun getString(key: String, defValue: String):String?{
        return repository.getString(key,defValue)
    }
    override fun edit(term:String){
        repository.edit(term)
    }
}