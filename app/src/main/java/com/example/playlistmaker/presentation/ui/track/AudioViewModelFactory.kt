package com.example.playlistmaker.presentation.ui.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AudioViewModelFactory () : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AudioViewModel::class.java)) {
            return AudioViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}