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
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.lifestyle.hope.Fragment.CautionDialogFragment
import com.example.lifestyle.hope.Fragment.ProgressDialogFragment
import com.example.lifestyle.hope.Models.MyFirebaseMessagingService
import com.example.lifestyle.hope.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import io.socket.client.IO
import io.socket.client.Socket


abstract class BaseActivity : AppCompatActivity() {
    var  progressDialogFragment : ProgressDialogFragment = ProgressDialogFragment()
    lateinit var notifi:ImageView
    lateinit var counter:TextView
    lateinit var toolbar: Toolbar
    lateinit var tvtitle: TextView
    val fragmentManager: FragmentManager = supportFragmentManager
    var drawerLayout: DrawerLayout? = null
    var mSocket: Socket = IO.socket("https://pure-shore-49093.herokuapp.com")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(getLayoutResourceId())
        super.onCreate(savedInstanceState)
        mSocket.connect()
        initComponent()
        setActionBar()
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAA", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val msg =token
                    Log.d("TTT", msg)
                })
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener { task ->
                    var msg = getString(R.string.msg_subscribed)
                    if (!task.isSuccessful) {
                        msg = getString(R.string.msg_subscribe_failed)
                    }
                    Log.d("OOP", msg)
                }
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

     }
    fun initComponent(){
        toolbar =findViewById(R.id.tb_toolbar)as Toolbar
        tvtitle = findViewById(R.id.tv_title)
        notifi = findViewById(R.id.iv_notification)
        counter = findViewById(R.id.tv_counter)
    }
    open fun setActionBar(){
        if(toolbar!= null){
            toolbar
            setSupportActionBar(toolbar)
            val actionBar = supportActionBar
            actionBar?.setDisplayShowTitleEnabled(false)

            if(actionBar!=null)
                actionBar?.setDisplayShowTitleEnabled(false)

            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
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
    fun showDialog(mess:String){
        var dialogLoginFragment: CautionDialogFragment = CautionDialogFragment()
        val args  = Bundle()
        args.putString("title",mess)
        dialogLoginFragment.arguments = args
        dialogLoginFragment.show(fragmentManager,null)
        dialogLoginFragment.isCancelable =false
    }
    fun showProgressDialog(isProgress:Boolean){
        if (isProgress && !progressDialogFragment.isAdded && !progressDialogFragment.isVisible){
            progressDialogFragment.show(fragmentManager,null)
            progressDialogFragment.isCancelable =false
        }
        else
            progressDialogFragment.dismiss()
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
         val transaction: FragmentTransaction = fragmentManager.beginTransaction()
         transaction.add(container,fragment)
         if(isBackStack) transaction.addToBackStack(null)
         transaction.commit()
     }
     fun replaceFrament(container: Int, fragment: Fragment, isBackStack: Boolean) {
         val transaction: FragmentTransaction = fragmentManager.beginTransaction()
         transaction.replace(container,fragment)
         if(isBackStack)
             transaction.addToBackStack(null)
         transaction.commit()
     }
 }