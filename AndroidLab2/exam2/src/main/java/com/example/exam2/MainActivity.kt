package com.example.exam2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exam2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = MyViewModel()
        binding.sumButton.setOnClickListener {
            val result = viewModel.calSum(binding.numberEditTextView.text.toString().toInt())
            result.observe(this) {
                binding.resultTextView.text = it
            }
        }
    }
}