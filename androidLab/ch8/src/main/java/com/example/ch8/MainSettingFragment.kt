package com.example.ch8

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

// 설정 변경 이벤트 처리
class MainSettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // 코드에서 설정 객체 획득
        val idPreference: EditTextPreference? = findPreference("id")
        val colorPreference: ListPreference? = findPreference("color")

        // summary를 코드에서 동적으로 제어
        // List Preference는 유저 설정 값 -> summary
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        // summary를 설정값을 포함.. 조작하고자 한다면 summary provider를 직접 만들어 적용
        idPreference?.summaryProvider =
            Preference.SummaryProvider<EditTextPreference> { preference ->
                val text = preference.text
                if (TextUtils.isEmpty(text)) {
                    "설정이 되지 않았습니다."
                } else {
                    "설정하신 ID는 $text 입니다."
                }
            }

        // 개별 preference 이벤트 : 유저 클릭
        idPreference?.setOnPreferenceClickListener {
            Log.d("MainSettingFragment", "setOnPreferenceClickListener...")
            true
        }

        // 값 변경 이벤트
        idPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Log.d("MainSettingFragment", "setOnPreferenceChangeListener... $newValue")
            true
        }
    }

    // 모든 설정 변경 시
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "id") {
            Log.d("MainSettingFragment",
                "onSharedPreferenceChanged... ${sharedPreferences?.getString("id", "")}")
        }
    }

    //fragment 가 화면에 보이는 순간..
    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }
    //fragment 화면이 포커스를 잃은 순간...
    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
}