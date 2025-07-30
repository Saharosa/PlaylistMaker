package com.example.playlistmaker


import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.TrackState.trackHistory
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.Stack

const val HISTORY_SAVE_KEY = "history_save_key"

class SearchActivity : AppCompatActivity() {
    var searchText=""
    var failedSearch=""
    val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    lateinit var sharePrefs:SharedPreferences
    lateinit var trackAdapterHistory:TrackAdapter
    override fun onResume(){
        super.onResume()
        trackAdapterHistory.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val itunesService = retrofit.create(ItunesApi::class.java)
        sharePrefs = getSharedPreferences(PLAY_LIST_MAKER, MODE_PRIVATE)
         trackAdapterHistory = TrackAdapter(TrackState.trackHistory,sharePrefs)
       super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonHome = findViewById<Button>(R.id.home)
        val buttonCross = findViewById<Button>(R.id.cross)
        val search = findViewById<EditText>(R.id.search)
        buttonHome.setOnClickListener{
            finish()
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val trackList = mutableListOf<Track>()
        val trackAdapter = TrackAdapter(trackList,sharePrefs)
        recyclerView.adapter = trackAdapter
        val textHistory = findViewById<TextView>(R.id.history_text)
        val clearHistory = findViewById<Button>(R.id.clear_history)
        search.setOnFocusChangeListener { _, hasFocus ->
            if (search.hasFocus() && search.text.toString() == "" && trackHistory.isNotEmpty()) {
                textHistory.isVisible = true
                clearHistory.isVisible = true
                trackAdapterHistory.notifyDataSetChanged()
                recyclerView.adapter = trackAdapterHistory
            }
            else {
                textHistory.isVisible = false
                clearHistory.isVisible = false
                recyclerView.adapter = trackAdapter
            }
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonCross.isVisible = !s.isNullOrEmpty()
                if (search.hasFocus() && search.text.toString() == "" && trackHistory.isNotEmpty()) {
                    textHistory.isVisible = true
                    clearHistory.isVisible = true
                    trackAdapterHistory.notifyDataSetChanged()
                    recyclerView.adapter = trackAdapterHistory
                }
                else {
                    textHistory.isVisible = false
                    clearHistory.isVisible = false
                    recyclerView.adapter = trackAdapter
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        search.addTextChangedListener(simpleTextWatcher)
        search.setText(searchText)
        val troubleConnection = findViewById<LinearLayout>(R.id.trouble_connection)
        val notFoundError = findViewById<LinearLayout>(R.id.not_found)
        val update = findViewById<Button>(R.id.update)
        buttonCross.setOnClickListener(){
            search.setText("")
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
            searchText=""
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(search.windowToken, 0)
        }
        fun trackSearch(term:String){
            troubleConnection.isVisible=false
            notFoundError.isVisible=false
            itunesService.search(term).enqueue(object:
                Callback<TrackResponse>{
                override fun onResponse(call: Call<TrackResponse>,response: Response<TrackResponse>){
                    if (response.isSuccessful) {
                        trackList.clear()
                        val results = response.body()?.results
                        if (results?.isNotEmpty() == true) {
                            trackList.addAll(results)
                            trackAdapter.notifyDataSetChanged()
                        }
                        if (trackList.isEmpty()) {
                            notFoundError.isVisible = true
                            textHistory.isVisible = false
                            clearHistory.isVisible = false
                            recyclerView.adapter = trackAdapter
                            trackAdapter.notifyDataSetChanged()
                        }
                    } else {
                        trackList.clear()
                        trackAdapter.notifyDataSetChanged()
                        failedSearch=term
                        troubleConnection.isVisible=true
                        textHistory.isVisible = false
                        clearHistory.isVisible = false
                        recyclerView.adapter = trackAdapter
                    }
                }
                override fun onFailure(call: Call<TrackResponse>, t: Throwable){
                    trackList.clear()
                    trackAdapter.notifyDataSetChanged()
                    troubleConnection.isVisible=true
                    textHistory.isVisible = false
                    clearHistory.isVisible = false
                    recyclerView.adapter = trackAdapter
                    failedSearch=term }
            })

        }
        update.setOnClickListener{
            trackSearch(failedSearch)
        }
        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                trackSearch(search.text.toString())
                true
            }
            false
        }
        clearHistory.setOnClickListener{
            trackHistory.clear()
            trackAdapterHistory.notifyDataSetChanged()
            textHistory.isVisible = false
            clearHistory.isVisible = false
        }
        trackHistory.clear()
        trackHistory.addAll(Gson().fromJson(sharePrefs.getString(HISTORY_SAVE_KEY,"[]"), Array<Track>::class.java))
        if (!trackHistory.isNullOrEmpty()){
            textHistory.isVisible = true
            clearHistory.isVisible = true
            trackAdapterHistory.notifyDataSetChanged()
            recyclerView.adapter = trackAdapterHistory
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchText",searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("searchText","")

    }
}
