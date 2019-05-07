package com.example.lifestyle.hope.Views.Users

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.Fragment.ShowProfileFragment
import com.example.lifestyle.hope.R

class ProfileActivity:BaseActivity(),View.OnClickListener {

    lateinit var edit:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.profile)
        var fragment:ShowProfileFragment = ShowProfileFragment()
        addFragment(R.id.fr_profile_box,fragment,false)

        anhXa()
    }
    fun anhXa() {

    }
    override fun onClick(v: View?) {

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_profile
    }
}