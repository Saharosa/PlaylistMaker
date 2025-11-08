package com.example.playlistmaker.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.ui.media.MediaActivity
import com.example.playlistmaker.presentation.ui.search.SearchActivity
import com.example.playlistmaker.presentation.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonSettings = findViewById<Button>(R.id.settings)
        buttonSettings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }
        val buttonMedia = findViewById<Button>(R.id.media)
        val buttonClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val displayIntent = Intent(this@MainActivity, MediaActivity::class.java)
                startActivity(displayIntent)
            }
        }
        buttonMedia.setOnClickListener(buttonClickListener)

        val buttonSearch = findViewById<Button>(R.id.search)
        buttonSearch.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }
    }
    }