package com.example.mission1.model

data class PageList(
    var id: Long,
    var totalResults: Long,
    var articles: List<Item>,
)