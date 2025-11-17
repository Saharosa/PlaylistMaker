package com.example.playlistmaker.presentation.ui.track

sealed interface  AudioState {

    object Playing : AudioState

    object Pause : AudioState

}