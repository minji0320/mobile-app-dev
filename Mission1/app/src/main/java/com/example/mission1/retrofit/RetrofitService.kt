package com.example.mission1.retrofit


import com.example.mission1.model.Item
import com.example.mission1.model.PageList
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    //https://newsapi.org/v2/everything?q=movies&apiKey=079dac74a5f94ebdb990ecf61c8854b7&pageSize=20&page=2

    @GET("/v2/everything")
    fun getNewsList(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Long,
    ): Call<PageList>


    companion object {
        val API_KEY = "079dac74a5f94ebdb990ecf61c8854b7"
    }
}