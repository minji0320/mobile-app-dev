package com.example.mission1.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.mission1.databinding.ItemMainBinding
import com.example.mission1.model.Item

class ItemAdapter(val datas: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = datas[position]
        holder.binding.data = item
    }

    override fun getItemCount(): Int = datas.size

}