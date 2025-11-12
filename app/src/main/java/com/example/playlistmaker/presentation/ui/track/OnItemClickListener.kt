package com.example.playlistmaker.presentation.ui.track

import android.content.SharedPreferences
import com.example.playlistmaker.domain.Track


interface OnItemClickListener{
    fun onItemClick(tracks: List<Track>, position: Int, prefs: SharedPreferences)
}