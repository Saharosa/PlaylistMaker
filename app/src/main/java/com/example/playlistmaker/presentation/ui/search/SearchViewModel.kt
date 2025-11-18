package com.example.playlistmaker.presentation.ui.search

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.track.Track
import com.example.playlistmaker.domain.search.api.HistoryInteractor
import com.example.playlistmaker.domain.search.api.TrackInteractor
class SearchViewModel(private val trackInteractor: TrackInteractor,private val trackHistory: HistoryInteractor): ViewModel() {
    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
    private val trackList = mutableListOf<Track>()
    private val handler = Handler(Looper.getMainLooper())
    private var lastTerm = ""
    private val searchRunnable =Runnable { searchDebounced(lastTerm)}
    private val stateLiveData = MutableLiveData<SearchState>()
    private val searchTextLiveData = MutableLiveData<String>()
    fun setSearchText(term:String){
        searchTextLiveData.postValue(term)
    }
    fun observeSearchText(): LiveData<String> = searchTextLiveData
    fun observeState(): LiveData<SearchState> = stateLiveData

    fun search(term:String) {
        lastTerm=term
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }
    private fun searchDebounced(term:String){
        if (term!=""){
            stateLiveData.postValue(SearchState.Loading)
            trackInteractor.searchTrack(term, object : TrackInteractor.TrackConsumer {
                override fun consume(result: Pair<List<Track>, Int>) {
                    Log.d("STATE", "serching " + term)
                    handler.post {
                        trackList.clear()
                        trackList.addAll(result.first)
                        val responseCode = result.second
                        if (result.second == 200) {
                            if (result.first.isNotEmpty()) {
                                stateLiveData.postValue(SearchState.Content(trackList))
                            } else {
                                stateLiveData.postValue(SearchState.Empty(responseCode.toString()))
                            }
                        }
                        if (result.second != 200) {
                            stateLiveData.postValue(SearchState.NoConnection(responseCode.toString()))
                        }
                    }
                }
            })
        }
    }
    fun addToHistory(tracks: List<Track>, position: Int){
        trackHistory.addToHistory(tracks,position)
    }
}