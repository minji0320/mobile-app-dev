package com.example.mission2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var author: String?,
    var title: String,
    var description: String?,
    var urlToImage: String,
    var url: String,
    var publishedAt: String)