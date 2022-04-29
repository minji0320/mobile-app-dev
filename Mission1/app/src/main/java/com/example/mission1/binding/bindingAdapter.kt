package com.example.mission1.binding

import android.annotation.SuppressLint
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mission1.util.getDate
import com.example.mission1.util.getTime

@SuppressLint("SetTextI18n")
@BindingAdapter("bind:publishedAt")
fun publishedAt(view: TextView, date: String) {
    view.text = "${getDate(date)} at ${getTime(date)}"
}

@BindingAdapter("bind:urlToImage")
fun urlToImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .override(250, 200)
        .into(view)
}

@BindingAdapter("bind:toUrl")
fun toUrl(view: WebView, url: String) {
    view.also {
        it.settings.run {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        it.loadUrl(url)
    }
}