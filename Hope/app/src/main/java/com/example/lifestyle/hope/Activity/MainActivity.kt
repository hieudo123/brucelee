package com.example.lifestyle.hope.Activity;

import android.content.Intent
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View;
import android.widget.*
import com.example.lifestyle.hope.Adapter.NewsAdapter

import com.example.lifestyle.hope.R;
import com.example.lifestyle.hope.Views.News.CreateNewsActivity

public class MainActivity : BaseActivity(),View.OnClickListener {



    var list= ArrayList<String>()
    lateinit var adapter:NewsAdapter
    lateinit var floatingActionButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        var drawerLayout: DrawerLayout
        super.onCreate(savedInstanceState)
        setLeftActionIcon(R.drawable.icons_menu_filled);
        setRecyclerViewAdapter()
        anhXa()

    }
    override fun getLayoutResourceId(): Int {
        return  R.layout.activity_main
    }

    protected override fun onLeftActionClicked() {
        if(drawerLayout!!.isDrawerOpen(Gravity.START))
            drawerLayout!!.closeDrawer(Gravity.START)
        else
            drawerLayout!!.openDrawer(Gravity.START)

    }
    fun setRecyclerViewAdapter()
    {

        for(i:Int in 0..10)
        {
            list.add("News" + i);
        }
        adapter = NewsAdapter(this,list)
        var recyclerView:RecyclerView
        recyclerView = findViewById(R.id.rv_news)
        var linearLayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter= adapter
    }
    fun anhXa()
    {
        drawerLayout = findViewById(R.id.drawerlayout)
        floatingActionButton = findViewById(R.id.fbtn_addnews) as FloatingActionButton
        floatingActionButton.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.fbtn_addnews ->{
                Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
                var intent = Intent(this, CreateNewsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
