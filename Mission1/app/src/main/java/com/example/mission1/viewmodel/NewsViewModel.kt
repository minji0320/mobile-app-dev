package com.example.mission1.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mission1.model.Item
import com.example.mission1.model.PageList
import com.example.mission1.repository.NewsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class NewsViewModel : ViewModel() {

    fun getNews(query: String): MutableLiveData<List<Item>> {
        val liveData = MutableLiveData<List<Item>>()

        NewsRepository().getNewsList(query, object : Callback<PageList> {
            override fun onResponse(call: Call<PageList>, response: Response<PageList>) {
                val results = response.body()?.articles ?: listOf()
                liveData.postValue(results)
            }

            override fun onFailure(call: Call<PageList>, t: Throwable) {
                call.cancel()
            }
        })

        return liveData
    }
}