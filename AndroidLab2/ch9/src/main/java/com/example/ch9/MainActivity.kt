package com.example.ch9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = (application as AppApplication).db.userDao()

        val user = User(0,"gildong", "hong")
        thread {
            dao.insertUSer(user)
        }

        thread {
            dao.getAll().forEach {
                Log.d("MainActivity", "${it.uid}, ${it.firstName}, ${it.lastName}")
            }
        }
    }
}