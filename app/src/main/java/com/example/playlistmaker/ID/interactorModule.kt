package com.example.playlistmaker.ID

import com.example.playlistmaker.domain.api.SharedPrefsInteractor
import com.example.playlistmaker.domain.impl.sharedPrefsInteractorImpl
import com.example.playlistmaker.domain.search.api.HistoryInteractor
import com.example.playlistmaker.domain.search.api.TrackInteractor
import com.example.playlistmaker.domain.search.impl.TrackHistoryInteractorImpl
import com.example.playlistmaker.domain.search.impl.TrackInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<TrackInteractor> { TrackInteractorImpl(get()) }
    single<HistoryInteractor> { TrackHistoryInteractorImpl(get()) }
    single<SharedPrefsInteractor> { sharedPrefsInteractorImpl(get()) }
}
