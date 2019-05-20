package com.example.lifestyle.hope.Views.News

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.CallBack
import com.example.lifestyle.hope.Fragment.BaseFragment
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.presenter.News.GetAllNews.PreHandlerNews

class NewsFragment:BaseFragment(),View.OnClickListener,ViewHandlerGetNews,CallBack {
    override fun onClick(position: Int) {
        var intent : Intent = Intent(context,NewsDetailActivity()::class.java)
        intent.putExtra("News",list[position])
        startActivity(intent)
    }


    lateinit var floatingActionButton: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    var list =  ArrayList<News>()
    var page = 1
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLastPage :ProgressBar
    lateinit var adapter:NewsAdapter
    lateinit var preHandlerNews: PreHandlerNews
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_news,container,false)
        Toast.makeText(context,"News",Toast.LENGTH_SHORT).show()
        recyclerView = view.findViewById(R.id.rv_news)
        init(view)
        getNews(page)
        loadInProgress()
        return view
    }
    fun init(view:View) {
        progressBar = view.findViewById(R.id.progress)
        progressBarLastPage = view.findViewById(R.id.prb_last_page)
        floatingActionButton = view.findViewById(R.id.fbtn_addnews) as FloatingActionButton
        floatingActionButton.setOnClickListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isLastVisiable()){
                    progressBarLastPage.visibility = View.VISIBLE
                        getNews(page)
                }
            }
        })
    }
    fun refresh(){
        list.clear()
        getNews(1)
        loadInProgress()
    }
    fun getNews(page : Int){
        preHandlerNews = PreHandlerNews(this.context!!, this, list)
        preHandlerNews.getAllNews(page)
    }
    //kiểm tra vị trí scroll có phải là cuối cùng không
    fun isLastVisiable(): Boolean {
        val layoutManager = (recyclerView.getLayoutManager() as LinearLayoutManager)
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = adapter.itemCount
        return (pos >= numItems - 1)
    }
    fun setAdapter()
    {
        adapter = NewsAdapter(this@NewsFragment.context!!,list)
        adapter.setClickItemListener(this@NewsFragment)
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
    override fun getAllNewsSuccess() {
        if(list.size <= 5){
            loadOnSuccess(true)
        }
        else
            loadOnSuccess(false)

    }

    override fun getAllNewsFail() {
        loadOnFail()
    }
    override fun loadInProgress(){
         progressBar.visibility = View.VISIBLE
     }
    override fun loadOnSuccess(isFirst :Boolean){
        page+=1
        //kiểm tra nếu nó là lần lấy data đầu tiên thì gọi là setAdapter
        if (isFirst){
            setAdapter()
            progressBar.visibility = View.GONE
        }
        //ngược lại lấy ra vị trí cuối và thay đổi adapter để ko bị load lại trang
        else{
            Log.e("PA",page.toString())
            val recyclerViewState: Parcelable = recyclerView.layoutManager!!.onSaveInstanceState()!!
            adapter.notifyDataSetChanged()
            recyclerView.layoutManager!!.onRestoreInstanceState(recyclerViewState)
        }
        progressBarLastPage.visibility = View.GONE
    }
    override fun loadOnFail(){
        Toast.makeText(context,"Kiểm tra kết nối...",Toast.LENGTH_SHORT).show()
    }
    override fun isLastPage() {
        progressBarLastPage.visibility = View.GONE
    }

}