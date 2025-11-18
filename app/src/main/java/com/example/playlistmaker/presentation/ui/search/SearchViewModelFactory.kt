package com.example.playlistmaker.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.domain.search.api.HistoryInteractor
import com.example.playlistmaker.domain.search.api.TrackInteractor

class SearchViewModelFactory(private val trackInteractor: TrackInteractor, private val historyInteractor: HistoryInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(trackInteractor, historyInteractor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}