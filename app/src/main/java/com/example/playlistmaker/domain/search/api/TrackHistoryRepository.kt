package com.example.playlistmaker.domain.search.api

import com.example.playlistmaker.domain.track.Track

interface TrackHistoryRepository {
    fun loadHistory():ArrayDeque<Track>
    fun saveHistory(trackHistory: ArrayDeque<Track>)
}
