package com.example.playlistmaker.presentation.ui.search


import android.annotation.SuppressLint
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.data.search.network.ItunesApi
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.track.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.search.api.HistoryInteractor
import com.example.playlistmaker.presentation.ui.track.AudioPlayerActivity
import com.example.playlistmaker.presentation.ui.track.OnItemClickListener
import com.example.playlistmaker.presentation.ui.track.TrackAdapter

class SearchActivity : AppCompatActivity(), OnItemClickListener {
    companion object {
         const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    var searchText=""
    var failedSearch=""
    val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    lateinit var trackAdapterHistory: TrackAdapter
    lateinit var trackHistory: HistoryInteractor
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume(){
        super.onResume()
        trackAdapterHistory.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        trackHistory = Creator.provideHistoryInteractor(this)
        val factory = SearchViewModelFactory(Creator.provideTracksInteractor(),trackHistory)
        val itunesService = retrofit.create(ItunesApi::class.java)
        val interactor = Creator.provideTracksInteractor()
        val trackList = mutableListOf<Track>()
        val trackAdapter = TrackAdapter(trackList,this)
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.cross.isVisible = !s.isNullOrEmpty()
                if (binding.search.hasFocus() && binding.search.text.toString() == "" && trackHistory.isNotEmpty()) {
                    binding.historyText.isVisible = true
                    binding.clearHistory.isVisible = true
                    binding.notFound.isVisible=false
                    trackAdapterHistory.notifyDataSetChanged()
                    binding.recyclerView.adapter = trackAdapterHistory
                    viewModel.search("")
                }
                else if (binding.search.hasFocus() && binding.search.text.toString() != ""){
                    binding.historyText.isVisible = false
                    binding.clearHistory.isVisible = false
                    binding.recyclerView.adapter = trackAdapter
                    viewModel.search(binding.search.text.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        trackAdapterHistory = TrackAdapter(trackHistory.getHistory(),this)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.home.setOnClickListener{
            finish()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = trackAdapter
        binding.search.setOnFocusChangeListener { _, hasFocus ->
            if (binding.search.hasFocus() && binding.search.text.toString() == "" && trackHistory.isNotEmpty()) {
                binding.historyText.isVisible = true
                binding.clearHistory.isVisible = true
                trackAdapterHistory.notifyDataSetChanged()
                binding.recyclerView.adapter = trackAdapterHistory
            }
            else {
                binding.historyText.isVisible = false
                binding.clearHistory.isVisible = false
                binding.recyclerView.adapter = trackAdapter
            }
        }
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(binding.search.text.toString())
            }
            false
        }
        binding.search.addTextChangedListener(simpleTextWatcher)
        binding.search.setText(searchText)
        binding.cross.setOnClickListener(){
            binding.search.setText("")
            trackList.clear()
            binding.recyclerView.adapter=trackAdapterHistory
            trackAdapterHistory.notifyDataSetChanged()
            searchText=""
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.search.windowToken, 0)
            binding.notFound.isVisible=false
            binding.troubleConnection.isVisible=false
            binding.pb.isVisible=false
        }
        binding.update.setOnClickListener {
            viewModel.search(failedSearch)
        }
        binding.clearHistory.setOnClickListener{
            trackHistory.clear()
            trackHistory.saveHistory()
            trackAdapterHistory.notifyDataSetChanged()
            binding.historyText.isVisible = false
            binding.clearHistory.isVisible = false
        }
        trackHistory.clear()
        trackHistory.loadHistory()
        if (!trackHistory.isNullOrEmpty()){
            binding.historyText.isVisible = true
            binding.clearHistory.isVisible = true
            trackAdapterHistory.notifyDataSetChanged()
            binding.recyclerView.adapter = trackAdapterHistory
        }
        viewModel.observeState().observe(this) {
            when (it){
                is SearchState.NoConnection-> {
                    Log.d("STATE","NoConnection_STATE")
                    binding.troubleConnection.isVisible=true
                    binding.pb.isVisible=false
                    binding.notFound.isVisible=false
                }
                is SearchState.Empty-> {
                    Log.d("STATE","Empty_STATE")
                    binding.troubleConnection.isVisible=false
                    binding.pb.isVisible=false
                    binding.notFound.isVisible=true
            }
                is SearchState.Content->{
                    Log.d("STATE","CONTENT_STATE ")
                    trackList.clear()
                    trackList.addAll(it.Track)
                    binding.recyclerView.adapter=trackAdapter
                    trackAdapter.notifyDataSetChanged()
                    binding.troubleConnection.isVisible=false
                    binding.pb.isVisible=false
                    binding.notFound.isVisible=false
                }
                is SearchState.Loading->{
                    Log.d("STATE","Loading_STATE")
                    binding.notFound.isVisible=false
                    binding.troubleConnection.isVisible=false
                    binding.pb.isVisible=true
                    trackList.clear()
                    trackAdapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.observeSearchText().observe(this){
            binding.search.setText(it)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.setSearchText(binding.search.text.toString())
    }

    override fun onItemClick(tracks: List<Track>, position: Int ) {

        for (track in tracks){
            Log.d("DEBUG IN FOR","CURENT TRACK IS "+track.artistName)
        }

        Log.d("DEBUG","CURENT TRACK IS "+tracks[position].artistName)
        val displayIntent = Intent(this, AudioPlayerActivity::class.java).apply{
            putExtra("current_track", tracks[position])
        }
        viewModel.addToHistory(tracks,position)
        startActivity(displayIntent)
    }
}

