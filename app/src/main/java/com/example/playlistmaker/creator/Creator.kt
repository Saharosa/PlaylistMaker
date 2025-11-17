package com.example.playlistmaker.creator

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.data.search.impl.TrackHistoryRepositoryImpl
import com.example.playlistmaker.data.search.impl.TrackRepositoryImpl
import com.example.playlistmaker.data.search.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.impl.TrackHistoryInteractorImpl
import com.example.playlistmaker.domain.impl.TrackInteractorImpl
import com.example.playlistmaker.domain.search.api.HistoryInteractor
import com.example.playlistmaker.domain.search.api.TrackHistoryRepository
import com.example.playlistmaker.domain.search.api.TrackInteractor
import com.example.playlistmaker.domain.search.api.TrackRepository
import com.example.playlistmaker.presentation.ui.settings.PLAY_LIST_MAKER
import com.google.gson.Gson

object Creator {
    lateinit var sharePrefs: SharedPreferences
    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TrackInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }

    private fun getHistoryRepository(): TrackHistoryRepository {
        val gson = Gson()
        return TrackHistoryRepositoryImpl(sharePrefs, gson)
    }

    fun provideHistoryInteractor(parent: Context): HistoryInteractor {
        sharePrefs = parent.getSharedPreferences(PLAY_LIST_MAKER, Context.MODE_PRIVATE)
        return TrackHistoryInteractorImpl(getHistoryRepository())
    }

}