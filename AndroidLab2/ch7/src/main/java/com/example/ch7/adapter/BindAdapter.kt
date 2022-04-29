package com.example.ch7.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindAdapter {
    // kapt 필요!
    @BindingAdapter("bind:sum")
    @JvmStatic
    fun funA(view: TextView, data: Int) {
        var sum = 0
        for (i in 1..data) {
            sum += i
        }

        view.text = "$sum"
    }
}