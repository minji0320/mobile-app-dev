package com.example.mission1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mission1.R
import com.example.mission1.databinding.FragmentWebBinding


class WebFragment(private val url: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentWebBinding.inflate(inflater)

        binding.url = url

        return binding.root
    }

}