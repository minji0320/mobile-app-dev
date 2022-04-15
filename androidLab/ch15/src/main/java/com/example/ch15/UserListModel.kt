package com.example.ch15

import com.google.gson.annotations.SerializedName

data class UserListModel(
    var page: String,
    @SerializedName("per_page")
    var perPage: String,
    var total: String,
    var totalPages: String,
    var data: List<UserModel>?
)