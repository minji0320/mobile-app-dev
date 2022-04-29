package com.example.ch9

import android.app.Application
import androidx.room.Room

class AppApplication : Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "test"
        ).build()
    }
}