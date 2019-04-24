package com.example.lifestyle.hope.Activity

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lifestyle.hope.R

 abstract class BaseActivity : AppCompatActivity() {
     lateinit var toolbar: Toolbar
     lateinit var tvtitle: TextView
     var drawerLayout: DrawerLayout? = null

     override fun onCreate(savedInstanceState: Bundle?) {
         setContentView(getLayoutResourceId())
         super.onCreate(savedInstanceState)
         toolbar =findViewById(R.id.tb_toolbar)as Toolbar
         tvtitle = findViewById(R.id.tv_title)
            if(toolbar!= null){
                toolbar
                setSupportActionBar(toolbar)
                val actionBar = supportActionBar
                actionBar?.setDisplayShowTitleEnabled(false)

                if(actionBar!=null)
                    actionBar?.setDisplayShowTitleEnabled(false)

                toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
                toolbar.setNavigationOnClickListener {
                    onLeftActionClicked()

                }
            }

     }

     protected fun setLeftActionIcon(resId: Int) {
         toolbar.setNavigationIcon(resId)
     }

     protected abstract fun getLayoutResourceId(): Int
     protected open fun onLeftActionClicked() {
         try {
             onBackPressed()
             Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
         } catch (e: Exception) {

             e.printStackTrace()
         }

     }

     override fun setTitle(resId: Int) {
         tvtitle.setText(resId)
         super.setTitle(resId)
     }

     override fun setTitle(cs: CharSequence) {
        tvtitle.setText(cs)
         super.setTitle(cs)
     }
 }