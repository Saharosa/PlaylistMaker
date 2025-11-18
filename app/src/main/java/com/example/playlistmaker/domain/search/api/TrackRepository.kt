package com.example.playlistmaker.domain.search.api

import com.example.playlistmaker.domain.track.Track

interface TrackRepository {
    fun searchTrack(expression: String): Pair<List<Track>,Int>
}