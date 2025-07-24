package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTime: String,// Продолжительность трека
    val artworkUrl100: String // Ссылка на изображение обложки
)

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val dayView: TextView = itemView.findViewById(R.id.day)
    private val tempView: TextView = itemView.findViewById(R.id.temp)

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.artworkUrl100)

    fun bind(model:Track){
        trackName.text = model.trackName
        artistName.text=model.artistName
        trackTime.text = model.trackTime
        artistName.text=model.temp.toString()
    }
}

class WeatherAdapter(private val weather: List<Weather>) : RecyclerView.Adapter<WeatherViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weather[position])
    }

    override fun getItemCount(): Int {
        return weather.size
    }
}

