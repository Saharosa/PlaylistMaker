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

interface OnItemClickListener{
    fun onItemClick( tracks: List<Track>, position: Int,prefs:SharedPreferences)
}

class TrackAdapter(private val tracks: List<Track>,val prefs:SharedPreferences, val listener: OnItemClickListener) : RecyclerView.Adapter<TrackViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(tracks,position,prefs)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}

