package com.example.playlistmaker.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.domain.api.TrackHistoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Deque

const val HISTORY_SAVE_KEY = "history_save_key"

class TrackHistoryRepositoryImpl(
    private val sharePrefs: SharedPreferences,
    private val gson: Gson
):TrackHistoryRepository{

    override fun loadHistory():Array<Track>{
        return gson.fromJson(sharePrefs.getString(HISTORY_SAVE_KEY,"[]"), Array<Track>::class.java)
    }
    override fun saveHistory(trackHistory: ArrayDeque<Track>) {
        sharePrefs.edit { putString(HISTORY_SAVE_KEY, gson.toJson(trackHistory)) }
    }
}
