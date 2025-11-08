package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.Track

interface TrackRepository {
    fun searchTrack(expression: String): Pair<List<Track>,Int>
}