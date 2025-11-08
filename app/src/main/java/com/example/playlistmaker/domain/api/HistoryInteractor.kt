package com.example.playlistmaker.domain.api

import android.content.SharedPreferences
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.presentation.ui.search.HISTORY_SAVE_KEY
import com.google.gson.Gson

interface HistoryInteractor {
        fun getHistory():List<Track>
        fun isNotEmpty():Boolean
        fun isNullOrEmpty():Boolean
        fun clear()
        fun checkAvailability(track: Track):Boolean
        fun addElement(track: Track)
        fun loadHistory()
        fun saveHistory()
}