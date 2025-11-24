package com.example.playlistmaker.presentation.ui.search

import com.example.playlistmaker.domain.track.Track


sealed interface SearchState {

    object Loading : SearchState

    data class Content(
        val Track: List<Track>,
        val waitOfContent: Boolean
    ) : SearchState

    data class NoConnection(
        val message: String
    ) : SearchState

    data class Empty(
        val message: String
    ) : SearchState

}

