package com.example.ch7

import android.content.ContentUris
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.ch7.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() = with(binding) {
        button1.setOnClickListener {
            val file = File(filesDir, "test.txt")
            val writeStream = file.writer()
            writeStream.write("hello world - internal...")
            writeStream.flush()

            val readStream: BufferedReader = file.reader().buffered()
            readStream.forEachLine {
                binding.textView.text = "$it"
            }
        }

        button2.setOnClickListener {
            val file = File(getExternalFilesDir(null), "test.txt")
            val writeStream = file.writer()
            writeStream.write("hello world - external...")
            writeStream.flush()

            val readStream: BufferedReader = file.reader().buffered()
            readStream.forEachLine {
                binding.textView.text = "$it"
            }
        }

        button3.setOnClickListener {
            // 갤러리 앱에서 관리하는 이미지 데이터의 정보 추출
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
            )

            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            cursor?.let {
                while (cursor.moveToNext()) {
                    Log.d("MainActivity",
                        "_id: ${cursor.getLong(0)}, name: ${cursor.getString(1)}")
                    val uri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getLong(0)
                    )
                    val resolver = applicationContext.contentResolver
                    resolver.openInputStream(uri).use {
                        // 안드로이드에서 이미지를 표현하는 객체가 두개 - Bitmap(원론), Drawable(추상화)
                        // Bitmap은 꼭 BitmapFactory에 의해서만 만들어짐
                        val option = BitmapFactory.Options()
                        option.inSampleSize = 10 // 10분의 1로 줄여서 로딩
                        val bitmap = BitmapFactory.decodeStream(it, null, option)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}