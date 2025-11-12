package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.data.TrackHistoryRepositoryImpl
import com.example.playlistmaker.data.TrackRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.HistoryInteractor
import com.example.playlistmaker.domain.api.TrackHistoryRepository
import com.example.playlistmaker.domain.api.TrackInteractor
import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.impl.TrackInteractorImpl
import com.example.playlistmaker.presentation.ui.track.TrackHistoryInteractorImpl
import com.google.gson.Gson

object Creator {
    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TrackInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }

    private fun getHistoryRepository(sharePrefs: SharedPreferences): TrackHistoryRepository {
        val gson = Gson()
        return TrackHistoryRepositoryImpl(sharePrefs,gson)
    }

    fun provideHistoryInteractor(sharePrefs: SharedPreferences): HistoryInteractor {
        return TrackHistoryInteractorImpl(getHistoryRepository(sharePrefs))
    }

}