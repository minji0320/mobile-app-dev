package com.example.ch8

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ch8.databinding.FragmentOneBinding
import com.example.ch8.databinding.FragmentTwoBinding


class TwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentTwoBinding.inflate(inflater)

        val model: MyViewModel by activityViewModels()

        binding.twoResultView.text = model.sum.toString()

        return binding.root
    }


}