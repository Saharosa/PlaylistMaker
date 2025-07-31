package com.example.playlistmaker

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val currentTrack = intent.getParcelableExtra<Track>("current_track")
        setContentView(R.layout.audio_player_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val home = findViewById<Button>(R.id.home)
        home.setOnClickListener{
            finish()
        }
        val name = findViewById<TextView>(R.id.trackName)
        val group = findViewById<TextView>(R.id.trackGroup)
        val duration = findViewById<TextView>(R.id.duration)
        val albom = findViewById<TextView>(R.id.albom)
        val year = findViewById<TextView>(R.id.year)
        val text_albom = findViewById<TextView>(R.id.text_albom)
        val text_year = findViewById<TextView>(R.id.text_year)
        val genre = findViewById<TextView>(R.id.genre)
        val country = findViewById<TextView>(R.id.country)
        name.text= currentTrack!!.trackName
        group.text= currentTrack.artistName
        duration.text= SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentTrack.trackTimeMillis)
        if (!currentTrack.collectionName.isNullOrEmpty()) albom.text = currentTrack.collectionName
        else {
            albom.isVisible = false
            text_albom.isVisible = false
        }
        if (!currentTrack.releaseDate.isNullOrEmpty()) year.text= currentTrack.releaseDate
        else {
            year.isVisible= false
            text_year.isVisible= false
        }
        genre.text = currentTrack.primaryGenreName
        country.text= currentTrack.country
        val cover = findViewById<ImageView>(R.id.trackCoverImage)
        Glide.with(this).load(currentTrack.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")).placeholder(R.drawable.placeholder).centerCrop().into(cover)
        }

}