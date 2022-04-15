package com.example.ch11

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import com.example.ch11.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    class JavaScriptTest {
        @get:JavascriptInterface
        val webData: String
            get() {
                // js에서 필요한 데이터 구성해서 리턴, 차트 그리기 위한 데이터
                val sb = StringBuffer()
                sb.append("[")
                for (i in 0..9) {
                    sb.append("[$i,")
                    sb.append("${(0..100).random()}")
                    sb.append("]")
                    if (i < 9) sb.append(",")
                }
                sb.append("]")
                return sb.toString()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.run {
            javaScriptEnabled = true
        }

        binding.webView.run {
            addJavascriptInterface(JavaScriptTest(), "android")
            loadUrl("file:///android_asset/test.html")

            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?,
                ): Boolean {
                    Toast.makeText(this@WebActivity, message, Toast.LENGTH_SHORT).show()
                    result?.confirm()
                    return true
                }
            }
        }

        binding.webButton.setOnClickListener {
            binding.webView.loadUrl("javascript:jsFunction('hello')")
        }
    }
}