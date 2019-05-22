package com.example.lifestyle.hope.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.lifestyle.hope.R
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.File


class ImageAdapter(var imagelist : ArrayList<String>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageAdapter.ViewHolder {
        var v: View = LayoutInflater.from(p0.context).inflate(R.layout.item_image,p0,false)
        return ImageAdapter.ViewHolder(v)
    }
    override fun getItemCount(): Int {
       return imagelist.size
    }

    override fun onBindViewHolder(p0: ImageAdapter.ViewHolder, i: Int) {
        var imgFile : File =  File(imagelist[i].trim());
        val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        p0.image.setImageBitmap(myBitmap)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.iv_image)
    }
}