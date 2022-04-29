package com.example.mission2.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mission2.model.Item


@Dao
interface NewsDAO {
    @Query("SELECT * FROM article")
    fun getNewsList(): PagingSource<Int, Item>

    @Insert
    fun insert(user: List<Item>)

    @Query("DELETE FROM article")
    fun deleteAll()
}