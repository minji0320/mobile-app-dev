package com.example.ch9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ch9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val datas: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this).readableDatabase
        var cursor = db.rawQuery("select * from TB_TODO", null)
        cursor.run {
            while (moveToNext()) {
                datas.add(cursor.getString(1))
            }
        }
        db.close()

        var result = ""
        datas.forEach {
            result += "$it \n"
        }
        binding.mainTextView.text = result

        // add 실행시켜서 되돌아 왔을 때 넘어온 결과를 처리해야 함
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data!!.getStringExtra("result")?.let {
                datas.add(it)
                var result = ""
                datas.forEach {
                    result += "$it \n"
                }
                binding.mainTextView.text = result
            }
        }

        binding.mainButton.setOnClickListener {
            launcher.launch(Intent(this, AddActivity::class.java))
        }
    }

}