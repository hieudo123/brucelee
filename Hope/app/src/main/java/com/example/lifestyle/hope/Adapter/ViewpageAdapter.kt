package com.example.lifestyle.hope.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.lifestyle.hope.R
import com.squareup.picasso.Picasso

class ViewpageAdapter(var list:ArrayList<String>,var context: Context): PagerAdapter() {
    var custom_position = 0
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1 as View
    }
    override fun getCount(): Int {
        return  list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layoutInflater : LayoutInflater = LayoutInflater.from(this.context)
        var view : View = layoutInflater.inflate(R.layout.item_viewpager,container,false)
        val image : ImageView = view.findViewById(R.id.iv_image)
        var Slider = list[position]
        Picasso.get().load(Slider).into(image)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }
}