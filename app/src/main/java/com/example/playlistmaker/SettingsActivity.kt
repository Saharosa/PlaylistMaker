package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonHome = findViewById<Button>(R.id.home)
        buttonHome.setOnClickListener {
           finish()
        }
        val buttonSupport = findViewById<Button>(R.id.support)
        buttonSupport.setOnClickListener{
            val message = R.string.message
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.mail))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,R.string.message_title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
        val buttonShare = findViewById<Button>(R.id.buttonShare)
        buttonShare.setOnClickListener {
            val message = getString(R.string.adURL)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, R.string.share_with.toString()))
        }
        val buttonUserAgreement = findViewById<Button>(R.id.userAgreement)
        buttonUserAgreement.setOnClickListener{
            val url = getString(R.string.ypURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        val switchDarkTheme = findViewById<Switch>(R.id.dark_theme)
        switchDarkTheme.setOnCheckedChangeListener {_, isChecked ->
            switchTheme(!isChecked)
        }
    }
}

fun switchTheme(isDark:Boolean):Boolean{
    if(!isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    return !isDark
}