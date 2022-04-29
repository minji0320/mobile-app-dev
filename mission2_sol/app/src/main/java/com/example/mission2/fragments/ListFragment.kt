package com.example.mission2.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mission2.R
import com.example.mission2.databinding.FragmentListBinding
import com.example.mission2.model.Item
import com.example.mission2.recyclerview.ItemAdapter
import com.example.mission2.viewmodel.NewsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.ViewModelProviders




@ExperimentalPagingApi
class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    lateinit var searchView: SearchView
    val viewModel: NewsViewModel by activityViewModels()
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)

        itemAdapter = ItemAdapter()

        binding.recycler.run {
            layoutManager = LinearLayoutManager(activity)
            adapter=itemAdapter

        }
        getData("travel")

        return binding.root
    }

    private fun getData(query: String) {
        Log.d("kkang","list fragment....")
        //add............
        lifecycleScope.launch{
            viewModel.getNews(query).collect {
                Log.d("kkang","list fragment....2, ")
                itemAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)

        searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                getData(query ?: "travel")
                return false
            }
        })
    }
}