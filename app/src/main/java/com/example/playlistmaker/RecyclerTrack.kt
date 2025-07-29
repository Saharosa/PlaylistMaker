package com.example.playlistmaker


import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.TrackState.trackHistory
import com.google.gson.Gson
import java.util.Locale

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.artworkUrl100)

    fun bind(model:Track){
        trackName.text = model.trackName
        artistName.text=model.artistName
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.placeholder).centerCrop().into(artworkUrl100)
    }
}

class TrackAdapter(private val tracks: List<Track>,val prefs:SharedPreferences) : RecyclerView.Adapter<TrackViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            var availability = false
            for (i in trackHistory){
                if (i.trackId==tracks[position].trackId){
                    val temp = i
                    trackHistory.remove(i)
                    trackHistory.addFirst(temp)
                    availability=true
                    break
                }
            }
            if (!availability){
                if(trackHistory.size<10){
                    trackHistory.addFirst(tracks[position])
                }
                else {
                    trackHistory.removeLast()
                    trackHistory.addFirst(tracks[position])
                }
            }
            prefs.edit().putString(HISTORY_SAVE_KEY, Gson().toJson(trackHistory)).apply()
            TrackState.currentTrack=tracks[position]
            val displayIntent = Intent(holder.itemView.context, AudioPlayerActivity::class.java)
            holder.itemView.context.startActivity(displayIntent)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}

