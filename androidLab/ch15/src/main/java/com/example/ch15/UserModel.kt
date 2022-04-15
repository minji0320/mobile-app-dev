package com.example.ch15

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

//server data 담길 dto 클래스..
data class UserModel(
    var id: String,
    @SerializedName("first_name")
    var firstName: String,
    var lastName: String,
    var avatar: String,
    var avatarBitmap: Bitmap
)