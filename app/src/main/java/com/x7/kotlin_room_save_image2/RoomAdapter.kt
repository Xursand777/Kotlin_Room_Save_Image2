package com.x7.kotlin_room_save_image2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x7.kotlin_room_save_image2.databinding.RecyclerviewItemBinding
import com.x7.kotlin_room_save_image2.room.User

class RoomAdapter constructor(
 val context: Context,
 val arrayList: ArrayList<User>
):RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view=RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.binding.apply {
            textviewforroom.text=arrayList.get(position).name
            imageviewforroom.setImageBitmap(byteArrayconverttoBitmap(arrayList.get(position).image))
        }
    }
    override fun getItemCount(): Int =arrayList.size

    class RoomViewHolder(val binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)



    fun byteArrayconverttoBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}