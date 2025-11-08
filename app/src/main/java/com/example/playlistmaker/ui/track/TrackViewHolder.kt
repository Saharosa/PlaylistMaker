package com.example.playlistmaker.ui.track


import android.icu.text.SimpleDateFormat
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Track
import java.util.Locale

private  val handler = Handler(Looper.getMainLooper())

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.artworkUrl100)

    fun bind(model: Track){
        trackName.text = model.trackName
        artistName.text=model.artistName
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.placeholder).centerCrop().into(artworkUrl100)
    }
}
