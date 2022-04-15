package com.example.ch9

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch9.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() = with(binding) {
        addButton.setOnClickListener {
            val inputData = addEditText.text.toString()

            val db = DBHelper(this@AddActivity).writableDatabase
            db.execSQL(
                "insert into TB_TODO (todo) values (?)",
                arrayOf(inputData)
            )
            db.close()

            // Main (db select - 출력) -> Add (db insert - 입력)
            val intent = intent
            intent.putExtra("result", inputData)
            setResult(Activity.RESULT_OK, intent)

            // 화면을 메인으로 전환
            finish()


        }
    }
}