package com.example.playlistmaker.presentation.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.domain.search.api.HistoryInteractor
import com.example.playlistmaker.domain.track.Track
import com.example.playlistmaker.presentation.ui.track.AudioPlayerFragment.Companion.ARGS_TRACK
import com.example.playlistmaker.presentation.ui.track.OnItemClickListener
import com.example.playlistmaker.presentation.ui.track.TrackAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class SearchFragment :Fragment(),OnItemClickListener {

    companion object {

        fun newInstance() = SearchFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
    var searchText=""
    var failedSearch=""
    val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    lateinit var trackAdapterHistory: TrackAdapter
    val trackHistory: HistoryInteractor by inject()
    private lateinit var binding: FragmentSearchBinding

    private  val viewModel: SearchViewModel by viewModel()
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume(){
        super.onResume()
        trackAdapterHistory.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        trackAdapterHistory = TrackAdapter(trackHistory.getHistory(),this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
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
        viewModel.observeState().observe(requireActivity()) {
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
                    if (it.waitOfContent) {
                        Log.d("STATE", "CONTENT_STATE ")
                        trackList.clear()
                        trackList.addAll(it.Track)
                        binding.recyclerView.adapter = trackAdapter
                        trackAdapter.notifyDataSetChanged()
                        binding.troubleConnection.isVisible = false
                        binding.pb.isVisible = false
                        binding.notFound.isVisible = false
                        viewModel.notifyContentIsShowing()
                    }
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
        viewModel.observeSearchText().observe(requireActivity()){
            binding.search.setText(it)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.setSearchText(binding.search.text.toString())
    }
    override fun onItemClick(tracks: List<Track>, position: Int ) {

         viewModel.addToHistory(tracks,position)
        val bundle = Bundle().apply {
            putParcelable(ARGS_TRACK, tracks[position])
        }
        findNavController().navigate(R.id.audioPlayerFragment,bundle)
    }
}