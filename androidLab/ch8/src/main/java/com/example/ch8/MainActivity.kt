package com.example.ch8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MainActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //아래의 layout xml 출력만으로도.. 설정 main 화면 잘 나온다..
        setContentView(R.layout.activity_main)
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        //이 함수까지는 자동으로 호출된다..
        //이곳에서 다른 설정 fragment 를 띄워야 한다..
        //어떤 fragment???? 라는 정보를 얻고 있다..

        //args 는 fragment 로 이동하면서..넘겨야 하는 데이터.. 있을 수도 있어서..
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, pref.fragment ?: "")

        fragment.arguments = args
        //화면 전환.. fragment 전환.. fragment 코어 코드이다..
        supportFragmentManager.beginTransaction().replace(R.id.settings, fragment)
            .addToBackStack(null).commit()
        return true
    }
}