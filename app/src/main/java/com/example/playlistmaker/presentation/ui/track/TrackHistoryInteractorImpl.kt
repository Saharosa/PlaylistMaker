package com.example.playlistmaker.presentation.ui.track

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.domain.api.HistoryInteractor
import com.example.playlistmaker.presentation.ui.search.HISTORY_SAVE_KEY
import com.google.gson.Gson

class TrackHistoryInteractorImpl(private val sharePrefs: SharedPreferences):HistoryInteractor{
    private val trackHistoryList:ArrayDeque<Track> =ArrayDeque()
    override fun getHistory():List<Track>{
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
        trackHistoryList.addAll(Gson().fromJson(sharePrefs.getString(HISTORY_SAVE_KEY,"[]"), Array<Track>::class.java))
    }

    override fun saveHistory() {
        sharePrefs.edit { putString(HISTORY_SAVE_KEY, Gson().toJson(trackHistoryList)) }
    }
}