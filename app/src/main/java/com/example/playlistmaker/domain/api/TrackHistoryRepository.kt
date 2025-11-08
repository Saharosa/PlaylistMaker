package com.example.playlistmaker.domain.api

import androidx.core.content.edit
import com.example.playlistmaker.domain.Track
import com.google.gson.Gson
import java.util.Deque

interface TrackHistoryRepository {
    fun loadHistory():Array<Track>
    fun saveHistory(trackHistory: ArrayDeque<Track>)
}
