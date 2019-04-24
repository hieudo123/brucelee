package com.example.lifestyle.hope.Views.Users

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.R

class ProfileActivity:BaseActivity(),View.OnClickListener {

    lateinit var edit:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.profile)
        anhXa()
    }
    fun anhXa()
    {
        edit = findViewById(R.id.tv_edit)
        edit.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.tv_edit ->{
                Toast.makeText(this,"Edit-now",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_profile
    }
}