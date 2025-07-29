package com.example.playlistmaker

object TrackState {
    var trackHistory:ArrayDeque<Track> = ArrayDeque<Track>()
    var currentTrack: Track = Track("","",2,"","","","","","")
}