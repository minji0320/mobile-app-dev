package com.example.mission1.model

// 기사 데이터 추상화
data class Item(
    var id: Long,
    var author: String?,
    var title: String,
    var description: String,
    var urlToImage: String, // image url
    var url: String,        // web site url
    var publishedAt: String,
)