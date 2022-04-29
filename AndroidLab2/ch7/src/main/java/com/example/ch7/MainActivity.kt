package com.example.ch7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch7.databinding.ActivityMainBinding
import com.example.ch7.model.User
import com.example.ch7.recycler.MyAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val user = User("gildong", "seoul", "01000000001")
        binding.user = user

        val list = mutableListOf<User>()
        for(i in 1..3) {
            list.add(User("name $i","seoul $i", "0100000000$i"))
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter(list)
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(this, "permission ok", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "permission no", Toast.LENGTH_SHORT).show()
            }
        }
    }
}