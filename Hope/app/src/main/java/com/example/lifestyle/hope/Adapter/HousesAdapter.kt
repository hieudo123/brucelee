package com.example.lifestyle.hope.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifestyle.hope.R

class HousesAdapter(var context: Context, var list:ArrayList<String>):RecyclerView.Adapter<HousesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var v: View = LayoutInflater.from(p0.context).inflate(R.layout.item_houses,p0,false)
        return HousesAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}