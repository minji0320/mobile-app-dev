package com.example.ch10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ch10.databinding.ActivityMainBinding
import com.example.ch10.recyclerview.UserAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel by viewModels<UserViewModel> { ViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = UserAdapter()
        binding.userRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.allUser.collect {
                adapter.submitData(it)
            }
        }
    }
}