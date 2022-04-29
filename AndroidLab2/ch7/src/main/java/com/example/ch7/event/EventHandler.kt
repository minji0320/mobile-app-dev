package com.example.ch7.event

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.ch7.MainActivity

object EventHandler {
    @JvmStatic
    fun onCallHandler(context: Context, phoneNumber: String) {
        // 함수 참조 : 이벤트 함수명 마음대로, 매개변수는 기본 이벤트 매개변수와 맞추어야 함
        // 리스너 : 함수명 마음대로, 매개변수 마음대로
        val activity = context as MainActivity
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            activity.startActivity(intent)
        } else {
            context.permissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    @JvmStatic
    fun onClick(data: String) {
        Log.d("EventHandler", "data:$data")
    }
}