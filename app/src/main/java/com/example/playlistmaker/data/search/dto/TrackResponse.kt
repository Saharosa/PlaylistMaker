package com.example.playlistmaker.data.search.dto

import com.example.playlistmaker.data.track.TrackDto

data class TrackResponse (
       val resultCount:Int,
    val results: List<TrackDto>
    ):Response()
