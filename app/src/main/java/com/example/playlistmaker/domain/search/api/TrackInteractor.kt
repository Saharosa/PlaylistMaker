package com.example.playlistmaker.domain.search.api

import com.example.playlistmaker.domain.track.Track

interface  TrackInteractor {
    fun searchTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(result: Pair<List<Track>,Int>)
    }
}