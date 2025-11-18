package com.example.playlistmaker.data.search.impl

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.search.dto.TrackResponse
import com.example.playlistmaker.data.search.dto.TrackSearchRequest
import com.example.playlistmaker.domain.track.Track
import com.example.playlistmaker.domain.search.api.TrackRepository

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {

    override fun searchTrack(expression: String): Pair<List<Track>,Int> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        if (response.resultCode == 200) {
            return Pair((response as TrackResponse).results.map {
                Track(it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl) },response.resultCode)
        } else {
            return Pair(emptyList(),0)
        }
    }
}