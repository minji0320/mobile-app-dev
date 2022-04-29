package com.example.ch10.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ch10.model.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM User WHERE ID BETWEEN :start and :end")
    fun getUsers(start: Int, end: Int): List<User>

    @Insert
    fun insert(user: List<User>)

    @Insert
    fun insert(user: User)
}