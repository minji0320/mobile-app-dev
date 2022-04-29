package com.example.mission2

import android.app.Application
import androidx.room.Room
import com.example.mission2.retrofit.RetrofitService
import com.example.mission2.room.AppDatabase

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppApplication: Application() {

    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "test"
        ).build()
    }

    companion object{
        fun apiService(): RetrofitService =
            Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)

    }
}