package com.example.ch15

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch15.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkService = (applicationContext as MyApplication).networkService
        val call = networkService.doGetUserList("1")
        call.enqueue(object : Callback<UserListModel>{
            override fun onResponse(call: Call<UserListModel>, response: Response<UserListModel>) {
                val userList = response.body()

                binding.mainListView.adapter = MyAdapter(this@MainActivity, R.layout.item_main, userList?.data)
            }

            override fun onFailure(call: Call<UserListModel>, t: Throwable) {
                call.cancel()
            }
        })
    }
}