package com.example.lifestyle.hope.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.lifestyle.hope.R
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import com.example.lifestyle.hope.CallBack
import com.squareup.picasso.Picasso
import java.io.File


class Image2Adapter(var imagelist : ArrayList<String>,var context: Context): RecyclerView.Adapter<Image2Adapter.ViewHolder>() {
    lateinit var callback  : CallBack
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Image2Adapter.ViewHolder {
        var v: View = LayoutInflater.from(p0.context).inflate(R.layout.item_images,p0,false)
        return Image2Adapter.ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return imagelist.size
    }
    fun setClickItemListener(callBackItem: CallBack)
    {
        callback = callBackItem
    }
    override fun onBindViewHolder(p0: Image2Adapter.ViewHolder, i: Int) {
       Picasso.get().load(imagelist[i].toString()).into(p0.image)
       p0.itemView.setOnClickListener {
            if (callback!=null){
                callback.onClick(i)
                Log.e("BBBBB",i.toString())
            }
        }
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.iv_image_item)
    }
}