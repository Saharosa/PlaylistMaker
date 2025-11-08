package com.example.playlistmaker

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
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
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 300L
    }
    private var timePause  = 0L
    private lateinit var stopwatch:TextView
    private var startTime = 0L
    private lateinit var playButton:ImageButton
    private var mediaPlayer = MediaPlayer()
    private fun preparePlayer() {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playButton.setBackgroundResource(R.drawable.pause)
            playButton.setImageResource(R.drawable.pause)
            playerState = STATE_PREPARED
        }
    }
    fun timeToLong(timeS:String):Long{
        val time = timeS.split(":")
        val min = time[0].toLong()
        val sec = time[1].toLong()
        return min*60*1000+sec*1000
    }
    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setBackgroundResource(R.drawable.play)
        playButton.setImageResource(R.drawable.play)
        if(playerState== STATE_PREPARED){
            playerState = STATE_PLAYING
            startStopWatch()
        }
        else {
            playerState = STATE_PLAYING
            startStopWatch(startTime+System.currentTimeMillis()-timePause)
            ///timeToLong(stopwatch.text.toString()))
            Log.d("chekw222222", java.lang.Long(startTime+System.currentTimeMillis()-timePause).toString())
        }

    }
    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setBackgroundResource(R.drawable.pause)
        playButton.setImageResource(R.drawable.pause)
        playerState = STATE_PAUSED
    }
    private fun playbackControl() {

        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
    lateinit var url:String
    val mainThreadHandler = Handler(Looper.getMainLooper())
    private var playerState = STATE_DEFAULT
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
        stopwatch = findViewById(R.id.timer)
        val name = findViewById<TextView>(R.id.trackName)
        val group = findViewById<TextView>(R.id.trackGroup)
        val duration = findViewById<TextView>(R.id.duration)
        val albom = findViewById<TextView>(R.id.albom)
        val year = findViewById<TextView>(R.id.year)
        val text_albom = findViewById<TextView>(R.id.text_albom)
        val text_year = findViewById<TextView>(R.id.text_year)
        val genre = findViewById<TextView>(R.id.genre)
        val country = findViewById<TextView>(R.id.country)
        playButton = findViewById<ImageButton>(R.id.play)
        url = currentTrack!!.previewUrl
        playButton.setOnClickListener(){
            playbackControl()
        }
        name.text= currentTrack!!.trackName
        group.text= currentTrack.artistName
        duration.text= SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentTrack.trackTimeMillis)
        if (!currentTrack.collectionName.isNullOrEmpty()) albom.text = currentTrack.collectionName
        else {
            albom.isVisible = false
            text_albom.isVisible = false
        }
        if (!currentTrack.releaseDate.isNullOrEmpty()) {
            year.text= currentTrack.releaseDate.split("T")[0]
        }
        else {
            year.isVisible= false
            text_year.isVisible= false
        }
        genre.text = currentTrack.primaryGenreName
        country.text= currentTrack.country
        val cover = findViewById<ImageView>(R.id.trackCoverImage)
        Glide.with(this).load(currentTrack.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")).placeholder(R.drawable.placeholder).centerCrop().into(cover)
        preparePlayer()
        Log.d("Create", "конец onCreate()")
    }
    override fun onPause() {
        super.onPause()
        pausePlayer()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
    private fun startStopWatch(startTimeE :Long= System.currentTimeMillis()){
        startTime=startTimeE
        mainThreadHandler?.post(
            createUpdatestopWatchTask(startTime)
        )
    }
    private fun createUpdatestopWatchTask(startTime: Long): Runnable {
        return object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                Log.d("MSG",elapsedTime.toString())
                val seconds = elapsedTime / 1000
                val minutes = seconds / 60
                val secs = seconds % 60
                if (playerState==2){
                    stopwatch?.text = String.format("%d:%02d", minutes, secs)
                mainThreadHandler?.postDelayed(this, DELAY)
                }
                else if (playerState == STATE_PREPARED) {
                    stopwatch.text = "0:00"
                    Log.d("OKKOKOKOKO", "00000")
                }
                else
                    timePause = System.currentTimeMillis()

            }
        }
    }
}