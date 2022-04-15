package com.example.ch9

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// table create, scheme update 추상화
// db file 명은 동적 처리 가능
class DBHelper(context: Context): SQLiteOpenHelper(context, "testdb", null, 1) {
    // app install 후 helper가 이용되는 최초 한번 호출됨
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table TB_TODO(" +
                "_id integer primary key autoincrement," +
                "todo not null)")
    }

    // DB version 정보 변경 시 호출됨
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}