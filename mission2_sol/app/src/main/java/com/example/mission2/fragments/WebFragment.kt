package com.example.mission2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.mission2.databinding.FragmentWebBinding


class WebFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWebBinding.inflate(layoutInflater)
        val bundle = arguments
        bundle?.let {
            WebFragmentArgs.fromBundle(bundle).also {
                binding.url = it.url
            }
        }
        return binding.root
    }


}