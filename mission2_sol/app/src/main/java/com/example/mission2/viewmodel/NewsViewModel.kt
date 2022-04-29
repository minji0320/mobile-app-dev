package com.example.mission2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*

import com.example.mission2.AppApplication
import com.example.mission2.datasource.NewsRemoteMediator
import com.example.mission2.model.Item
import com.example.mission2.model.PageList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


@ExperimentalPagingApi
class NewsViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var application: AppApplication
    init {
        application = app as AppApplication
    }

    fun getNews(query: String): Flow<PagingData<Item>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            initialLoadSize = 10
        ),
        remoteMediator = NewsRemoteMediator(application).also {
            it.searchTxt = query
        }
    ) {
        application.db.newsDAO().getNewsList()
    }.flow.cachedIn(viewModelScope)




}