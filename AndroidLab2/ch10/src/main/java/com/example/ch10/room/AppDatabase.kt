package com.example.ch10.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ch10.model.User
import kotlin.concurrent.thread

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "test")
                    .addCallback(object : RoomDatabase.Callback() {//초기 데이터 구축
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            val dataList = arrayListOf<User>()
                            thread {
                                for(i in 1..100){
                                    dataList.add(User(0, "hong $i"))
                                }
                                get(context).userDAO().insert(dataList)
                            }
                        }
                    }).build()
            }
            return instance!!
        }
    }
}