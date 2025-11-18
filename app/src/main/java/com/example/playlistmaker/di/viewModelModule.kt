package com.example.playlistmaker.di

import com.example.playlistmaker.presentation.ui.search.SearchViewModel
import com.example.playlistmaker.presentation.ui.track.AudioViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(),get())
    }
    viewModel { AudioViewModel(get()) }
}