package com.example.playlistmaker

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.google.gson.Gson

class HistorySave {
    fun save(prefs:SharedPreferences,tracks:MutableList<Track>){
        prefs.edit().putString(HISTORY_SAVE_KEY,Gson().toJson(tracks)).apply()
    }
    fun load(prefs:SharedPreferences,tracks:MutableList<Track>){
        val json = prefs.getString(HISTORY_SAVE_KEY, null)
        tracks.addAll(Gson().fromJson(json,Array<Track>::class.java))
    }
}