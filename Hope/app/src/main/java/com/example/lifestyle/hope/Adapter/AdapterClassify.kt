package com.example.lifestyle.hope.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.lifestyle.hope.Models.Area
import com.example.lifestyle.hope.Models.Classify
import com.example.lifestyle.hope.R


class AdapterClassify(var list : ArrayList<Classify>,var activity: Activity) : BaseAdapter() {
    lateinit var inflater: LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        inflater =activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view : View? = convertView;
        view = inflater.inflate(R.layout.item_spinner,null)
        var areaName : TextView = view.findViewById(R.id.tv_area_name)
        areaName.setText(list[position].classify_name)
        return  view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}