package com.example.lifestyle.hope.Activity

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.lifestyle.hope.R

import io.socket.client.IO
import io.socket.client.Socket


abstract class BaseActivity : AppCompatActivity() {

    lateinit var notifi:ImageView
    lateinit var counter:TextView
    lateinit var toolbar: Toolbar
    lateinit var tvtitle: TextView
    var drawerLayout: DrawerLayout? = null
    lateinit var mSocket: Socket
    override fun onCreate(savedInstanceState: Bundle?) {
         setContentView(getLayoutResourceId())
         super.onCreate(savedInstanceState)
         toolbar =findViewById(R.id.tb_toolbar)as Toolbar
         tvtitle = findViewById(R.id.tv_title)
         notifi = findViewById(R.id.iv_notification)
         counter = findViewById(R.id.tv_counter)
         mSocket = IO.socket("https://pure-shore-49093.herokuapp.com")
         mSocket.connect()
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
     fun hideLeftActionIcon(hide: Boolean){
         if (hide){
             toolbar.setNavigationIcon(null)
         }

     }
     protected fun setLeftActionIcon(resId: Int) {
         toolbar.setNavigationIcon(resId)
     }

     protected abstract fun getLayoutResourceId(): Int
      open fun onLeftActionClicked() {
         try {
             onBackPressed()
         } catch (e: Exception) {

             e.printStackTrace()
         }

     }
     fun HideIconRight(hide:Boolean) {
         if(hide) {
             notifi.visibility = View.GONE
             counter.visibility = View.GONE
         }
         else {
             notifi.visibility = View.VISIBLE
             counter.visibility = View.VISIBLE
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
     fun addFragment(container:Int, fragment: Fragment, isBackStack:Boolean) {
         val fragmentManager: FragmentManager = supportFragmentManager
         val transaction: FragmentTransaction = fragmentManager.beginTransaction()
         transaction.add(container,fragment)
         if(isBackStack) transaction.addToBackStack(null)
         transaction.commit()
     }
     fun replaceFrament(container: Int, fragment: Fragment, isBackStack: Boolean) {
         val fragmentManager: FragmentManager = supportFragmentManager
         val transaction: FragmentTransaction = fragmentManager.beginTransaction()
         transaction.replace(container,fragment)
         if(isBackStack)
             transaction.addToBackStack(null)
         transaction.commit()
     }
 }