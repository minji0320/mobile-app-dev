package com.example.ch8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment, OneFragment())
            commit()
        }

        binding.buttonTwo.setOnClickListener {
            supportFragmentManager.beginTransaction().run {
                add(R.id.fragment, TwoFragment())
                commit()
            }
        }
    }
}