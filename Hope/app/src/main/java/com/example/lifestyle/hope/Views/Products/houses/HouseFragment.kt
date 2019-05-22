package com.example.lifestyle.hope.Views.Products.houses

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import com.example.lifestyle.hope.Adapter.HousesAdapter
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.Fragment.BaseFragment
import com.example.lifestyle.hope.Models.Area
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.Area.ViewHandlerGetAreas
import com.example.lifestyle.hope.presenter.Area.PreHandlerGetAreas

class HouseFragment:BaseFragment(),ViewHandlerGetAreas {


    var list= ArrayList<String>()
    var listArea = ArrayList<Area>()
    lateinit var preHandlerGetAreas: PreHandlerGetAreas

    lateinit var progressBar: ProgressBar
    lateinit var progressBarLastPage : ProgressBar
    lateinit var adapter:HousesAdapter
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_houses,container,false)
        init(view)
        var hanlder = Handler()
        var runnable = Runnable {
            loadInProgress()
            loadAllHouse()
        };hanlder.postDelayed(runnable,1000)
        return view
    }
    fun init(v:View) {
        progressBar = v.findViewById(R.id.progress)
        progressBarLastPage = v.findViewById(R.id.prb_last_page)
        recyclerView = v.findViewById(R.id.rv_houses)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isLastVisiable()){
                    progressBarLastPage.visibility = View.VISIBLE
                    var handler = Handler()
                    var runnable = Runnable {
                        loadAllHouse()
                    }; handler.postDelayed(runnable, 1000)

                }
            }
        })
    }
    fun getAreas(){
        preHandlerGetAreas = PreHandlerGetAreas(this.context!!,this,listArea)
        preHandlerGetAreas.getAreas()
    }
    fun loadAllHouse(){
        for(i:Int in 0..10-1)
        {
            list.add("House" + i);
        }
        if(list.size <=10)
            loadOnSuccess(true)
        else
            loadOnSuccess(false)
    }

    fun isLastVisiable(): Boolean {
        val LinearLayoutManager = (recyclerView.getLayoutManager() as LinearLayoutManager)
        val pos = LinearLayoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = adapter.itemCount
        return (pos >= numItems - 1)
    }

    fun setAdapter(){
        adapter = HousesAdapter(this!!.context!!,list)
        var LinearLayoutManager= LinearLayoutManager(context)
        recyclerView.layoutManager=LinearLayoutManager
        recyclerView.adapter= adapter
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
        Toast.makeText(context,"Kiểm tra kết nối...", Toast.LENGTH_SHORT).show()
    }
    override fun getOnSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOnFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}