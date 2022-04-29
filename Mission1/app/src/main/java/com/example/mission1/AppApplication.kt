package com.example.mission1

import android.app.Application
import com.example.mission1.retrofit.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppApplication : Application() {
    companion object {
        fun apiService(): RetrofitService = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}