package com.example.ch10

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ch10.room.AppDatabase
import java.lang.IllegalArgumentException

class ViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            val userDao = AppDatabase.get(app).userDAO()
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("unknown")
    }

}