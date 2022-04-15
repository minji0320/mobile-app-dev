package com.example.ch14

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.os.persistableBundleOf

class MainActivity : AppCompatActivity() {

    lateinit var telephonyMagager: TelephonyManager

    private fun callChange() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            telephonyMagager.registerTelephonyCallback(
                mainExecutor,
                object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                    override fun onCallStateChanged(p0: Int) {
                        when(p0){
                            TelephonyManager.CALL_STATE_IDLE -> Log.d("kkang","call... idle...")
                            TelephonyManager.CALL_STATE_OFFHOOK -> Log.d("kkang","call... offhook...")
                            TelephonyManager.CALL_STATE_RINGING -> Log.d("kkang","call... ringing...")
                        }
                    }
                }
            )
        }else {
            val listener = object : PhoneStateListener() {
                override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                    when(state){
                        TelephonyManager.CALL_STATE_IDLE -> Log.d("kkang","call... idle...")
                        TelephonyManager.CALL_STATE_OFFHOOK -> Log.d("kkang","call... offhook...")
                        TelephonyManager.CALL_STATE_RINGING -> Log.d("kkang","call... ringing...")
                    }
                }
            }
            telephonyMagager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun phoneInfo() {
        val country = telephonyMagager.networkCountryIso
        val operator = telephonyMagager.networkOperatorName
        val phoneNumber = telephonyMagager.line1Number

        Log.d("kkang","$country, $operator, $phoneNumber")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        telephonyMagager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        //퍼미션 체크하고 들어가야 한다...
        //체크, 퍼미션 요구(다이얼로그 - 시스템), 사후판단..
        //체크는... 이전 버전과 동일..
        //요구, 사후판단.. 코드가.. 과거 deprecated... RequestActivityResult 이용...

        //activity 내에서... 외부에 무언가를 요청한후...업무진행 결과를 callback 으로 받아야 하는 경우...
        //퍼미션 요청.. 콜백, 다른 activity 실행후 결과 콜백...
        //원래.. 따로.. 구현...
        //모든... 액티비티 외부 실행, 콜백을 일원화 하는 방식으로.. 제공..

        //외부 요청 실행자.., 결과 콜백을 가지는 객체...
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()//요청을 실행하는 객체.. intent - StartActivityForResult
            //permission - 단일 - RequestPermission, 여러개...
        ){
            //콜백..
            if(it.all { permission -> permission.value == true}){
                callChange()
                phoneInfo()
            }else {
                Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT).show()
            }
        }

        //퍼미션 체크...
        if(ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") ==
                PackageManager.PERMISSION_GRANTED){
            callChange()
            phoneInfo()
        }else {
            //퍼미션 요구.. launcher 구동시켜서..
            permissionLauncher.launch(
                arrayOf(
                    "android.permission.READ_PHONE_NUMBERS",
                    "android.permission.READ_PHONE_STATE"
                )
            )
        }
    }
}