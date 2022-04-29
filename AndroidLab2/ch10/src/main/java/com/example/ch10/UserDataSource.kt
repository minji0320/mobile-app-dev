package com.example.ch10

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ch10.model.User
import com.example.ch10.room.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 데이터 획득 역할
class UserDataSource(private val dao: UserDAO) : PagingSource<Int, User>() {
    // coroutines flow로 발행할 것
    suspend fun getUser(start: Int, end: Int): List<User> = withContext(Dispatchers.IO) {
        dao.getUsers(start, end)
    }


    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // preKey -> null => 첫 페이지
        // nextKey -> null => 마지막 페이지
        // 둘다 null -> 초기 상태
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val page = params.key ?: 1
            val start = (page - 1) * params.loadSize + 1
            val end = start + params.loadSize
            val response = getUser(start, end)
            val preKey = if (page == 1) null else page - 1

            return LoadResult.Page(
                data = response,
                prevKey = preKey,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}