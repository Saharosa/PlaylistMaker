package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.Track

interface HistoryInteractor {
        fun getHistory():ArrayDeque<Track>
        fun isNotEmpty():Boolean
        fun isNullOrEmpty():Boolean
        fun clear()
        fun checkAvailability(track: Track):Boolean
        fun addElement(track: Track)
        fun loadHistory()
        fun saveHistory()
}