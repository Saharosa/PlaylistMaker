package com.example.playlistmaker

import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class SearchActivity : AppCompatActivity() {
    var searchText=""
    override fun onCreate(savedInstanceState: Bundle?) {
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
        search.setOnClickListener{
            search.requestFocus()
        }
        buttonHome.setOnClickListener(){
            finish()
        }
        buttonCross.setOnClickListener(){
            search.setText("")
            searchText=""
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(search.windowToken, 0)
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonCross.isVisible = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        search.addTextChangedListener(simpleTextWatcher)
        search.setText(searchText)
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