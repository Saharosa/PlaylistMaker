package com.example.playlistmaker

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
        if (savedInstanceState != null) {
            Toast.makeText(this, "Сохранение загружается", Toast.LENGTH_SHORT).show()
            searchText = savedInstanceState.getString("searchText","2").toString()
            search.setText(searchText)
        }
        else Toast.makeText(this, "Сохранение ne загружается", Toast.LENGTH_SHORT).show()
        buttonHome.setOnClickListener(){
            val displayIntent = Intent(this,MainActivity::class.java)
            startActivity(displayIntent)
        }
        buttonCross.setOnClickListener(){
            search.setText("")
            searchText=""
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(s.isNullOrEmpty()){
                    buttonCross.visibility= View.GONE
                }
                else buttonCross.visibility=View.VISIBLE
                searchText = search.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        search.addTextChangedListener(simpleTextWatcher)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchText",searchText)
        Toast.makeText(this, "Состояние сохранено "+searchText, Toast.LENGTH_SHORT).show()
    }
}