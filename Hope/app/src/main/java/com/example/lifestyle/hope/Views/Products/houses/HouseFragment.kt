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
import com.example.lifestyle.hope.Adapter.AdapterArea
import com.example.lifestyle.hope.Adapter.AdapterClassify
import com.example.lifestyle.hope.Adapter.HousesAdapter
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.Fragment.BaseFragment
import com.example.lifestyle.hope.Models.Area
import com.example.lifestyle.hope.Models.Classify
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.Area.ViewHandlerGetAreas
import com.example.lifestyle.hope.Views.Classify.ViewHandlerGetClassify
import com.example.lifestyle.hope.presenter.Area.PreHandlerGetAreas
import com.example.lifestyle.hope.presenter.Classify.PreHandlerGetClassify
import com.example.lifestyle.hope.presenter.Classify.PreImpGetClassify
import java.lang.reflect.AccessibleObject.setAccessible



class HouseFragment:BaseFragment(),ViewHandlerGetAreas,ViewHandlerGetClassify {


    lateinit var spinner: Spinner
    lateinit var spinnerClassify: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLastPage : ProgressBar
    lateinit var recyclerView: RecyclerView

    //List
    var list= ArrayList<String>()
    var listArea = ArrayList<Area>()
    var listClassify = ArrayList<Classify>()
    //Adapter
    lateinit var adapter:HousesAdapter
    lateinit var adapterArea: AdapterArea
    lateinit var adapterClassify: AdapterClassify

    //PreHandler
    lateinit var preHandlerGetAreas: PreHandlerGetAreas
    lateinit var preHandlerGetClassify: PreHandlerGetClassify
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_houses,container,false)
        init(view)
        var hanlder = Handler()
        var runnable = Runnable {
            loadInProgress()
            loadAllHouse()
        };hanlder.postDelayed(runnable,1000)
        getAreas()
        getClassify()
        return view
    }
    fun init(v:View) {
        spinner = v.findViewById(R.id.sp_spinner)
        spinnerClassify = v.findViewById(R.id.sp_class)
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
        setupSpinner(spinner)
        setupSpinner(spinnerClassify)
    }
    fun setupSpinner(spinner : Spinner){
        try {
            val popup = Spinner::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true

            // Get private mPopup member variable and try cast to ListPopupWindow
            val popupWindow = popup.get(spinner) as android.widget.ListPopupWindow

            // Set popupWindow height to 500px
            popupWindow.height = dp2px(248)
        } catch (e: NoClassDefFoundError) {
            // silently fail...
        } catch (e: ClassCastException) {
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {
        }

    }
    fun getAreas(){
        preHandlerGetAreas = PreHandlerGetAreas(this.context!!,this,listArea)
        preHandlerGetAreas.getAreas()
    }
    fun getClassify(){
        preHandlerGetClassify = PreHandlerGetClassify(listClassify, this.context!!,this)
        preHandlerGetClassify.getClassify()
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
    protected fun dp2px(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
    override fun loadOnFail(){
        Toast.makeText(context,"Kiểm tra kết nối...", Toast.LENGTH_SHORT).show()
    }
    override fun getOnSuccess() {
        adapterArea = AdapterArea(listArea, this.activity!!)
        spinner.adapter = adapterArea
    }

    override fun getInProgress() {

    }

    override fun getOnFail() {

    }
    override fun getClassifyOnSuccess() {
        adapterClassify = AdapterClassify(listClassify, this.activity!!)
        spinnerClassify.adapter = adapterClassify
    }

    override fun getClassifyOnFail() {

    }

}