package com.example.mission1.binding

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.mission1.MainActivity
import com.example.mission1.R
import com.example.mission1.fragments.WebFragment

object EventHandler {
    // 이 함수 호출 시, Fragment 교체 필요
    // liveData 많이 이용함

    val urlLiveData = MutableLiveData<String>()

    @JvmStatic
    fun onVisitClick(url: String) {
        urlLiveData.value = url
    }
}
