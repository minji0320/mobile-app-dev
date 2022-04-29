package com.example.mission2.datasource

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.mission2.AppApplication
import com.example.mission2.model.Item
import com.example.mission2.model.RemoteKey
import com.example.mission2.retrofit.RetrofitService


@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(val application: AppApplication): RemoteMediator<Int, Item>() {
    private val newsDao = application.db.newsDAO()
    private val remoteKeyDao = application.db.getKeysDao()

    var searchTxt: String = "travel"

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): MediatorResult {
        try {

            var loadKey = when (loadType) {

                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = application.db.withTransaction {
                        remoteKeyDao.remoteKeyByQuery("news")
                    }
                    if (remoteKey.nextKey == null) {
                        return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }


            val page: Int = loadKey?.let { loadKey + 1 } ?: 1

            val data: List<Item> = AppApplication.apiService().getNewsList(
                q = searchTxt,
                apiKey = RetrofitService.API_KEY,
                page = page.toLong(),
                pageSize = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            ).body()?.articles ?: listOf<Item>()

            application.db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDao.deleteAll()
                    remoteKeyDao.deleteByQuery("news")
                }

                remoteKeyDao.insertOrReplace(RemoteKey( "news", page))

                newsDao.insert(data)
            }
            Log.d("kkang","mediator.... ${data.size}")

            return MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

}
