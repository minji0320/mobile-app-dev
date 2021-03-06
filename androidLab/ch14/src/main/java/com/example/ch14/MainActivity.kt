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

        //????????? ???????????? ???????????? ??????...
        //??????, ????????? ??????(??????????????? - ?????????), ????????????..
        //?????????... ?????? ????????? ??????..
        //??????, ????????????.. ?????????.. ?????? deprecated... RequestActivityResult ??????...

        //activity ?????????... ????????? ???????????? ????????????...???????????? ????????? callback ?????? ????????? ?????? ??????...
        //????????? ??????.. ??????, ?????? activity ????????? ?????? ??????...
        //??????.. ??????.. ??????...
        //??????... ???????????? ?????? ??????, ????????? ????????? ?????? ????????????.. ??????..

        //?????? ?????? ?????????.., ?????? ????????? ????????? ??????...
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()//????????? ???????????? ??????.. intent - StartActivityForResult
            //permission - ?????? - RequestPermission, ?????????...
        ){
            //??????..
            if(it.all { permission -> permission.value == true}){
                callChange()
                phoneInfo()
            }else {
                Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT).show()
            }
        }

        //????????? ??????...
        if(ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") ==
                PackageManager.PERMISSION_GRANTED){
            callChange()
            phoneInfo()
        }else {
            //????????? ??????.. launcher ???????????????..
            permissionLauncher.launch(
                arrayOf(
                    "android.permission.READ_PHONE_NUMBERS",
                    "android.permission.READ_PHONE_STATE"
                )
            )
        }
    }
}