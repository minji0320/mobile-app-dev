package com.example.ch11

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import com.example.ch11.databinding.ActivityMainBinding
import com.example.ch11.databinding.ItemMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topData = listOf<Map<String, String>>(
            mapOf("name" to "kim", "phone" to "0100001"),
            mapOf("name" to "kang", "phone" to "0100002"),
        )

        val adapter = SimpleAdapter(
            this,
            topData,
            android.R.layout.simple_list_item_2,
            arrayOf("name", "phone"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.topListView.adapter = adapter

        val bottomDatas = listOf<User>(
            User("kim", "0100001"),
            User("kang", "0100002"),
        )

        binding.bottomListView.adapter = MyAdapter(this, R.layout.item_main, bottomDatas)

        binding.bottomListView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, bottomDatas[i].name, Toast.LENGTH_SHORT).show()
        }
    }
}

data class User(val name: String, val phone: String)

class MyAdapter(context: Context, val resId: Int, val datas: List<User>) :
    ArrayAdapter<User>(context, resId) {

    lateinit var binding: ItemMainBinding

    override fun getCount(): Int {
        return datas.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        convertView?.let {
            binding = convertView.tag as ItemMainBinding
        } ?: let {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = ItemMainBinding.inflate(inflater)
            binding.root.tag = binding
        }

        val user = datas[position]
        binding.nameView.text = "${user.name}"

        binding.callView.setOnClickListener {
            Toast.makeText(context, "calll : ${user.phone}", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}