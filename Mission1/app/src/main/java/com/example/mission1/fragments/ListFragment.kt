package com.example.mission1.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission1.R
import com.example.mission1.databinding.FragmentListBinding
import com.example.mission1.model.Item
import com.example.mission1.recyclerview.ItemAdapter
import com.example.mission1.util.getDate
import com.example.mission1.viewmodel.NewsViewModel
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    lateinit var searchView: SearchView
    lateinit var binding: FragmentListBinding
    val viewModel: NewsViewModel by viewModels()
    var datas = mutableListOf<Item>()
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        itemAdapter = ItemAdapter(datas)

        binding.recycler.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = itemAdapter

            getData("travel")
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(query: String) {
        viewModel.getNews(query).observe(viewLifecycleOwner) {
            datas.clear()
            datas.addAll(it)
            itemAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)

        searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 요청이 들어올 때
                getData(query ?: "travel")
                return false
            }
        })
    }
}