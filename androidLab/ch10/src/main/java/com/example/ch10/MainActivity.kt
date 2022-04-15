package com.example.ch10

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.ch10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu?.findItem(R.id.menu3)
        val searchView = menuItem?.actionView as SearchView

        // 검색 이벤트 등록, 추천 단어 서비스
        val searchAutoComplete: SearchView.SearchAutoComplete =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setBackgroundColor(Color.RED)
        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_orange_light)

        val datas = arrayOf(
            "android",
            "and",
            "apple"
        )
        searchAutoComplete.setAdapter(ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            datas
        ))

        searchAutoComplete.setOnItemClickListener { parent, view, position, id ->
            val query = parent.getItemAtPosition(position) as String
            searchAutoComplete.setText(query)
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.setQuery("", false)
                searchView.isIconified = true
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> Toast.makeText(this, "menu1 click..", Toast.LENGTH_SHORT).show()
            R.id.menu2 -> Toast.makeText(this, "menu2 click..", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}