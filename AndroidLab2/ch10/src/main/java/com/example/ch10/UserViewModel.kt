package com.example.ch10

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ch10.model.User
import com.example.ch10.room.UserDAO
import kotlinx.coroutines.flow.Flow

class UserViewModel(private val dao: UserDAO) : ViewModel() {

    val allUser: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            initialLoadSize = 10
        )
    ) {
        UserDataSource(dao)
    }.flow.cachedIn(viewModelScope)

}