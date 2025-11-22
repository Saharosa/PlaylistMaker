package com.example.playlistmaker.presentation.ui.track

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentAudioPlayerBinding
import com.example.playlistmaker.domain.track.Track
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import kotlin.getValue

class AudioPlayerFragment : Fragment() {
    companion object {
        const val ARGS_TRACK = "ARGS_TRACK"
        fun newInstance() = AudioPlayerFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    lateinit var url:String
    val viewModel: AudioViewModel by viewModel()
    private lateinit var binding: FragmentAudioPlayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentTrack: Track? = requireArguments().getParcelable(ARGS_TRACK)
        binding.home.setOnClickListener{
            findNavController().popBackStack()
        }
        url = currentTrack!!.previewUrl
        binding.play.setOnClickListener(){
            viewModel.playbackControl()
        }
        binding.trackName.text= currentTrack!!.trackName
        binding.trackGroup.text= currentTrack.artistName
        binding.duration.text= SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentTrack.trackTimeMillis)
        if (!currentTrack.collectionName.isNullOrEmpty()) binding.albom.text = currentTrack.collectionName
        else {
            binding.albom.isVisible = false
            binding.albom.isVisible = false
        }
        if (!currentTrack.releaseDate.isNullOrEmpty()) {
            binding.year.text= currentTrack.releaseDate.split("T")[0]
        }
        else {
            binding.year.isVisible= false
            binding.textYear.isVisible= false
        }
        binding.genre.text = currentTrack.primaryGenreName
        binding.country.text= currentTrack.country
        Glide.with(this).load(currentTrack.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")).placeholder(
            R.drawable.placeholder
        ).centerCrop().into(binding.trackCoverImage)
        viewModel.preparePlayer(url)
        Log.d("Create", "конец onCreate()")
        viewModel.observeState().observe(this){
            when(it){
                is AudioState.Playing -> {
                    binding.play.setBackgroundResource(R.drawable.play)
                    binding.play.setImageResource(R.drawable.play)
                }
                is AudioState.Pause -> {
                    binding.play.setBackgroundResource(R.drawable.pause)
                    binding.play.setImageResource(R.drawable.pause)
                }
            }
        }
        viewModel.observeTime().observe(this){
            binding.timer.text=it
        }
    }

}