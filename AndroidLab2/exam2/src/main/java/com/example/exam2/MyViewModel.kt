package com.example.exam2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel() : ViewModel() {
    fun calSum(num: Int): MutableLiveData<String> {
        var sum = 0
        val liveData = MutableLiveData<String>()

        for (i in 1..num) {
            sum += i
        }

        liveData.postValue(sum.toString())
        return liveData
    }
}