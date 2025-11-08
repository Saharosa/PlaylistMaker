package com.example.playlistmaker.presentation.ui.track

import android.util.Log
import androidx.core.content.edit
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.domain.api.HistoryInteractor
import com.example.playlistmaker.domain.api.TrackHistoryRepository
import com.google.gson.Gson


class TrackHistoryInteractorImpl(val repository: TrackHistoryRepository):HistoryInteractor{
    private val trackHistoryList: ArrayDeque<Track> =ArrayDeque<Track>()

    override fun getHistory():ArrayDeque<Track>{
        return trackHistoryList
    }
    override fun isNotEmpty():Boolean{
        return trackHistoryList.isNotEmpty()
    }
    override fun isNullOrEmpty():Boolean{
        return trackHistoryList.isNullOrEmpty()
    }
    override fun clear(){
        trackHistoryList.clear()
    }
    override fun addElement(track:Track){
        if(trackHistoryList.size<10){
            trackHistoryList.addFirst(track)
        }
        else {
            trackHistoryList.removeLast()
            trackHistoryList.addFirst(track)
        }
    }
    override fun checkAvailability(track:Track):Boolean{
        for (i in trackHistoryList){
            if (i.trackId==track.trackId){
                val temp = i
                trackHistoryList.remove(i)
                trackHistoryList.addFirst(temp)
                return true
            }
        }
        return false
    }
    override fun loadHistory(){
        trackHistoryList.addAll(repository.loadHistory())
    }

    override fun saveHistory() {
        repository.saveHistory(trackHistoryList)
    }
}