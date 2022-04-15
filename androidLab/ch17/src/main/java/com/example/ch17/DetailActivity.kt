package com.example.ch17

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch17.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //사진을 실행시킨 intent 로 부터 넘어온 extra 데이터 추출...
        val data1 = intent.getStringExtra("data1")
        val data2 = intent.getIntExtra("data2", 0)

        binding.detailResultView.text = "data1 : $data1, data2: $data2"

        binding.detailButton.setOnClickListener {
            //결과 데이터 담아서.. 전달..
            intent.putExtra("result","world")
            //결과를 리턴 시켜야 하는데.. 요청한 결과를 어떻게 처리해서 리턴한 것인지를 명시...
            setResult(RESULT_OK, intent)
            //자신을 종료시켜서 자동으로 이전 activity 나오게...
            finish()
        }
    }
}