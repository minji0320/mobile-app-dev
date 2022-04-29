package com.example.mission1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.example.mission1.binding.EventHandler

import com.example.mission1.fragments.ListFragment
import com.example.mission1.fragments.WebFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment, ListFragment())
            commit()
        }

        EventHandler.urlLiveData.observe(this) {
            supportFragmentManager.beginTransaction().run {
                add(R.id.fragment, WebFragment(it))
                addToBackStack(null)
                commit()
            }
        }
    }
}