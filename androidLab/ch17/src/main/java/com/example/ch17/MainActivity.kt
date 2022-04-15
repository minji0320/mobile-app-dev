package com.example.ch17

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ch17.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("data1","hello")
        intent.putExtra("data2",100)

        binding.goDetailButton1.setOnClickListener {
            startActivityForResult(intent, 10)
        }

        //Launcher 이용 방법...
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            val resultData = it.data?.getStringExtra("result")
            binding.mainResultView.text = "result : $resultData"
        }

        binding.goDetailButton2.setOnClickListener {
            launcher.launch(intent)
        }
    }
    //startActivityForResult 에 의한 요청이 되돌아 올때 자동 호출...
    //requestCode : intent 는 발생시킨 곳에서 intent 를 식별하기 위해 준 개발자 임의 숫자..
    //resultCode : intent 에 의해 실행된 곳에서 요청을 어떻게 처리해서 되돌린거야...
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == RESULT_OK){
            val result = data?.getStringExtra("result")
            binding.mainResultView.text = "result : $result"
        }
    }
}