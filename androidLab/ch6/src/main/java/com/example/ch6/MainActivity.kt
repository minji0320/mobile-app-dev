package com.example.ch6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import com.example.ch6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // back button : 3초 이내 2번 알고리즘 잡기 위해
    var initTime = 0L

    var pauseTime = 0L

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() = with(binding) {
        startButton.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            chronometer.start()

            stopButton.isEnabled = true
            resetButton.isEnabled = true
            startButton.isEnabled = false
        }

        stopButton.setOnClickListener {
            pauseTime = chronometer.base - SystemClock.elapsedRealtime()
            chronometer.stop()

            stopButton.isEnabled = false
            resetButton.isEnabled = true
            startButton.isEnabled = true
        }

        resetButton.setOnClickListener {
            pauseTime = 0L;
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.stop()

            stopButton.isEnabled = false
            resetButton.isEnabled = false
            startButton.isEnabled = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "종료하려면 한번 더 누르세요..", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }
}