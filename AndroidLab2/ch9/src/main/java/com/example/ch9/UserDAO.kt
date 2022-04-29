package com.example.ch9

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert
    fun insertUSer(user: User)
}