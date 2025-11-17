package com.example.playlistmaker.data.search.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.playlistmaker.data.track.TrackDto
import com.example.playlistmaker.domain.track.Track
import com.example.playlistmaker.domain.search.api.TrackHistoryRepository
import com.google.gson.Gson

const val HISTORY_SAVE_KEY = "history_save_key"
class TrackHistoryRepositoryImpl(private val sharePrefs: SharedPreferences, private val gson: Gson) : TrackHistoryRepository {
    override fun loadHistory(): ArrayDeque<Track> {
        val historyJson = sharePrefs.getString(HISTORY_SAVE_KEY, "[]")
        val historyDto = gson.fromJson(historyJson, Array<TrackDto>::class.java) ?: emptyArray()
        return ArrayDeque(historyDto.map {
            Track(
                trackName = it.trackName,
                artistName = it.artistName,
                trackTimeMillis = it.trackTimeMillis,
                artworkUrl100 = it.artworkUrl100,
                trackId = it.trackId,
                collectionName = it.collectionName,
                releaseDate = it.releaseDate,
                primaryGenreName = it.primaryGenreName,
                country = it.country,
                previewUrl = it.previewUrl
            )
        })
    }
    override fun saveHistory(trackHistory: ArrayDeque<Track>) {
        val dtoList = trackHistory.map {
            TrackDto(
                trackName = it.trackName,
                artistName = it.artistName,
                trackTimeMillis = it.trackTimeMillis,
                artworkUrl100 = it.artworkUrl100,
                trackId = it.trackId,
                collectionName = it.collectionName,
                releaseDate = it.releaseDate,
                primaryGenreName = it.primaryGenreName,
                country = it.country,
                previewUrl = it.previewUrl
            )
        }
        sharePrefs.edit { putString(HISTORY_SAVE_KEY, gson.toJson(dtoList)) }
    }
}
