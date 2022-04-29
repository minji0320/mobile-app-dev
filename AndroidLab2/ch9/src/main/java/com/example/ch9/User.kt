package com.example.ch9

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(

    @field:PrimaryKey(autoGenerate = true)
    var uid: Int,

    @field:ColumnInfo(name = "first_name")
    var firstName: String?,

    @field:ColumnInfo(name = "last_name")
    var lastName: String?,

)