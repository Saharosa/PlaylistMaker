package com.example.playlistmaker.presentation.ui.track

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue


class AudioViewModel(    val mediaPlayer:MediaPlayer): ViewModel()  {
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 300L
    }
    private val stateLiveData = MutableLiveData<AudioState>()
    fun observeState(): LiveData<AudioState> = stateLiveData

    private val timeLiveData = MutableLiveData<String>()
    fun observeTime(): LiveData<String> = timeLiveData
    private var timePause  = 0L
    private var startTime = 0L
    private var playerState = STATE_DEFAULT
    val mainThreadHandler = Handler(Looper.getMainLooper())

    fun createUpdatestopWatchTask(startTime: Long): Runnable {
        return object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                Log.d("MSG",elapsedTime.toString())
                val seconds = elapsedTime / 1000
                val minutes = seconds / 60
                val secs = seconds % 60
                if (playerState==2){
                    timeLiveData.postValue(String.format("%d:%02d", minutes, secs))
                    mainThreadHandler.postDelayed(this, DELAY)
                }
                else if (playerState == STATE_PREPARED) {
                    timeLiveData.postValue("0:00")
                }
                else
                    timePause = System.currentTimeMillis()

            }
        }
    }
    fun preparePlayer(url:String) {
        if(playerState==STATE_DEFAULT){
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                playerState = STATE_PREPARED
            }
            mediaPlayer.setOnCompletionListener {
                playerState = STATE_PREPARED
            }
        }
    }
    fun startPlayer() {
        mediaPlayer.start()
        if(playerState== STATE_PREPARED){
            playerState = STATE_PLAYING
            stateLiveData.postValue(AudioState.Playing)
            startStopWatch()
        }
        else {
            playerState = STATE_PLAYING
            stateLiveData.postValue(AudioState.Playing)
            startStopWatch(startTime+System.currentTimeMillis()-timePause)
        }

    }
    fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        stateLiveData.postValue(AudioState.Pause)
    }
     fun playbackControl() {

        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
    fun startStopWatch(startTimeE :Long= System.currentTimeMillis()){
        startTime=startTimeE
        mainThreadHandler?.post(
            createUpdatestopWatchTask(startTime)
        )
    }
    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}