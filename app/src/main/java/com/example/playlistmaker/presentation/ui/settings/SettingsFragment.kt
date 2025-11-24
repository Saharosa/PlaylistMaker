package com.example.playlistmaker.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.domain.api.SharedPrefsInteractor
import org.koin.android.ext.android.inject
import kotlin.getValue

class SettingsFragment : Fragment() {

    companion object {
        const val PLAY_LIST_MAKER="play_list_maker"
        const val THEME_KEY = "them_key"
        fun newInstance() = SettingsFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharePrefInteractor : SharedPrefsInteractor by inject()
        binding.support.setOnClickListener{
            val message = R.string.message
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.mail))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.message_title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
        binding.buttonShare.setOnClickListener {
            val message = getString(R.string.adURL)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, R.string.share_with.toString()))
        }
        binding.userAgreement.setOnClickListener{
            val url = getString(R.string.ypURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        binding.themeSwitcher.setOnCheckedChangeListener {switcher, isChecked ->
            if(isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharePrefInteractor.edit(isChecked.toString())
        }
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            binding.themeSwitcher.isChecked=true
        }
        else binding.themeSwitcher.isChecked=false

    }

}