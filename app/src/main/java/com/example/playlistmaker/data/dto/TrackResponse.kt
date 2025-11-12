package com.example.playlistmaker.data.dto

import com.example.playlistmaker.domain.Track

data class TrackResponse (
       val resultCount:Int,
    val results: List<TrackDto>
    ):Response()
