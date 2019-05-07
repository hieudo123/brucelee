package com.example.lifestyle.hope.Views.News

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.Fragment.BaseFragment
import com.example.lifestyle.hope.R
import com.github.ybq.android.spinkit.style.WanderingCubes

class NewsFragment:BaseFragment(),View.OnClickListener {
    lateinit var floatingActionButton: FloatingActionButton

    lateinit var recyclerView: RecyclerView
    var list= ArrayList<String>()
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLastPage :ProgressBar
    lateinit var adapter:NewsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_news,container,false)
        Toast.makeText(context,"News",Toast.LENGTH_SHORT).show()
        recyclerView = view.findViewById(R.id.rv_news)
        init(view)
        loadInProgress()
        var handler = Handler()
        var runnable = Runnable {
            loadAllNews()
        }; handler.postDelayed(runnable, 1000)
        return view
    }
    fun init(view:View)
    {
        progressBar = view.findViewById(R.id.progress)
        progressBarLastPage = view.findViewById(R.id.prb_last_page)
        floatingActionButton = view.findViewById(R.id.fbtn_addnews) as FloatingActionButton
        floatingActionButton.setOnClickListener(this)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isLastVisiable()){
                    progressBarLastPage.visibility = View.VISIBLE
                    var handler = Handler()
                    var runnable = Runnable {
                        loadAllNews()
                    }; handler.postDelayed(runnable, 1000)

                }
            }
        })
    }

    fun isLastVisiable(): Boolean {
        val layoutManager = (recyclerView.getLayoutManager() as LinearLayoutManager)
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = adapter.itemCount
        return (pos >= numItems - 1)
    }
    fun loadAllNews(){
        for(i:Int in 0..10-1)
        {
            list.add("News" + i);
        }
        if(list.size <= 10)
            loadOnSuccess(true)
        else
            loadOnSuccess(false)
    }
    fun setAdapter()
    {
        adapter = NewsAdapter(this@NewsFragment.context!!,list)
        var linearLayoutManager= LinearLayoutManager(context)
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter= adapter
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fbtn_addnews ->{
                Toast.makeText(context,"OK",Toast.LENGTH_SHORT).show()
                var intent = Intent(context, CreateNewsActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun loadInProgress(){
         progressBar.visibility = View.VISIBLE
     }
    override fun loadOnSuccess(isFirst :Boolean){
        if (isFirst){
            setAdapter()
            progressBar.visibility = View.GONE
        }
        else{
            val recyclerViewState: Parcelable = recyclerView.layoutManager!!.onSaveInstanceState()!!
            adapter.notifyDataSetChanged()
            recyclerView.layoutManager!!.onRestoreInstanceState(recyclerViewState)
        }
        progressBarLastPage.visibility = View.GONE
    }
    override fun loadOnFail(){
        Toast.makeText(context,"Kiểm tra kết nối...",Toast.LENGTH_SHORT).show()
    }
}