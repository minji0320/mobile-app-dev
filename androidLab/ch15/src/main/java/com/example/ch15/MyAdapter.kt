package com.example.ch15

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.ch15.databinding.ItemMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAdapter(context: Context, val resId: Int, val datas: List<UserModel>?): ArrayAdapter<UserModel>(context, resId) {
    override fun getCount(): Int {
        return datas?.size ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var binding: ItemMainBinding
        convertView ?.let {
            binding = convertView.tag as ItemMainBinding
        } ?: let {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = ItemMainBinding.inflate(inflater, parent, false)
            binding.root.tag = binding
        }

        val user = datas?.get(position)
        binding.id.text = user?.id
        binding.firstNameView.text = user?.firstName
        binding.lastNameView.text = user?.lastName

        user?.avatar?.let {
            //이미지 url 로 서버 네트워킹...
            val call = (context.applicationContext as MyApplication).networkService.getAvatarImage(it)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        if(response.body() != null){
                            //retrofit 을 이용해 보기 위해서.. 이미지 다운로드를 retrofit 으로 처리한 것이다..
                                //좀더 편하게 작성하려면.. 이미지 전문 라이브러리를 이용할 수도 있다.. Glide 같은 애들...
                            val bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
                            binding.avatarView.setImageBitmap(bitmap)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        return binding.root
    }
}