package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.Track

interface  TrackInteractor {
    fun searchTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(result: Pair<List<Track>,Int>)
    }
}