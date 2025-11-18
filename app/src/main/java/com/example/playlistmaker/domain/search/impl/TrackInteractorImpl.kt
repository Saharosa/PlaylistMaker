package com.example.playlistmaker.domain.search.impl
import com.example.playlistmaker.domain.search.api.TrackInteractor
import com.example.playlistmaker.domain.search.api.TrackRepository
import java.util.concurrent.Executors


class TrackInteractorImpl(private val repository: TrackRepository) : TrackInteractor {

    private val executor = Executors.newCachedThreadPool()
    override fun searchTrack(expression: String, consumer: TrackInteractor.TrackConsumer) {
        executor.execute {
            consumer.consume(repository.searchTrack(expression)) }
    }
}