package com.example.playlistmaker.ui.track

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Track

private const val CLICK_DEBOUNCE_DELAY = 1000L
private  val handler = Handler(Looper.getMainLooper())

class TrackAdapter(private val tracks: List<Track>, val prefs: SharedPreferences, val listener: OnItemClickListener) : RecyclerView.Adapter<TrackViewHolder> (){
    private var isClickAllowed = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            if(clickDebounce()) {
                Log.d("click", "нажато")
                listener.onItemClick(tracks, position, prefs)
            }
            else  Log.d("click", "низя")
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}
