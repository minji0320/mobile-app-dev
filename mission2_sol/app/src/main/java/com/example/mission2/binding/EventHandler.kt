package com.example.mission2.binding

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.mission2.MainActivity
import com.example.mission2.R
import com.example.mission2.fragments.ListFragmentDirections
import com.example.mission2.fragments.WebFragment


class EventHandler {

    fun onVisitClick(context: Context, url: String) {
        val navHostFragment = (context as MainActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val directions = ListFragmentDirections.actionListFragmentToWebFragment(url=url)
        navController.navigate(directions)
    }
}