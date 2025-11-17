package com.example.playlistmaker.domain.search.api

import com.example.playlistmaker.domain.track.Track

interface HistoryInteractor {
        fun getHistory():ArrayDeque<Track>
        fun isNotEmpty():Boolean
        fun isNullOrEmpty():Boolean
        fun clear()
        fun checkAvailability(track: Track):Boolean
        fun addElement(track: Track)
        fun loadHistory()
        fun saveHistory()
    fun addToHistory(tracks: List<Track>, position: Int)
}