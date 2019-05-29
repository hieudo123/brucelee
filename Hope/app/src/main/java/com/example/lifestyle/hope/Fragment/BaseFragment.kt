package com.example.lifestyle.hope.Fragment

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Toast
import com.example.lifestyle.hope.Activity.BaseActivity

open class BaseFragment:Fragment() {
   fun addFragment(container:Int, fragment: Fragment, isBackStack:Boolean) {
      if(activity is BaseActivity){
         (activity as BaseActivity).addFragment(container,fragment,isBackStack)
      }
   }
   fun replaceFrament(container:Int, fragment: Fragment, isBackStack:Boolean) {
      if(activity is BaseActivity){
         (activity as BaseActivity).replaceFrament(container,fragment,isBackStack)
      }
   }
   fun showProgressDialog(isProgress:Boolean){
      if(activity is BaseActivity){
         (activity as BaseActivity).showProgressDialog(isProgress)
      }
   }
   open fun loadInProgress(){

   }
   open fun loadOnSuccess(isFirst :Boolean){

   }
   open fun loadOnFail(){

   }
}