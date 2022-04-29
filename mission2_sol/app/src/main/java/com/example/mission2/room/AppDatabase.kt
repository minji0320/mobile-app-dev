package com.example.mission2.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mission2.model.Item
import com.example.mission2.model.RemoteKey
import java.util.concurrent.Executors

@Database(entities = [Item::class, RemoteKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDAO(): NewsDAO
    abstract fun getKeysDao(): RemoteKeyDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "NewsDatabase"
                ).build()
            }
            return instance!!
        }
    }
}