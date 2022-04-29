package com.example.mission1.repository

import com.example.mission1.AppApplication
import com.example.mission1.model.Item
import com.example.mission1.model.PageList
import com.example.mission1.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    fun getNewsList(query: String, callback: Callback<PageList>) {
        val apiService = AppApplication.apiService()
        apiService.getNewsList(
            q = query,
            apiKey = RetrofitService.API_KEY,
            page = 1,
            pageSize = 10
        ).enqueue(callback)
    }
}