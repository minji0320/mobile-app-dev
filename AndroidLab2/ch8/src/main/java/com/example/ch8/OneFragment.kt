package com.example.ch8

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ch8.databinding.FragmentOneBinding


class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentOneBinding.inflate(inflater)

        val model: MyViewModel by activityViewModels()

        binding.oneButton.setOnClickListener {
            model.calSum().observe(viewLifecycleOwner) {
                binding.oneResultView.text = it
            }
        }

        return binding.root
    }
}