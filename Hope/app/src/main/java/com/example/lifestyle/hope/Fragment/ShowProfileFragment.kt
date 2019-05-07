package com.example.lifestyle.hope.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.utils.SharePref
import com.squareup.picasso.Picasso
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.facebook.login.widget.ProfilePictureView
import java.net.URL


class ShowProfileFragment : BaseFragment(),View.OnClickListener {
    lateinit var avartar : ProfilePictureView
    var userid : String = ""
    lateinit var editProfile:TextView
    lateinit var sharePref:SharePref
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_show_profile,container,false)


        init(view)
        return view
    }
    fun init(v:View){
        editProfile = v.findViewById(R.id.tv_edit)
        avartar = v.findViewById(R.id.iv_avartar)

        editProfile.setOnClickListener(this)
        sharePref = SharePref(context)
        userid = sharePref.getString("userid","")
        if (userid != null) {
            Log.e("AAA", userid.replace(" ", ""))
            userid = userid.trim()
            avartar.setProfileId(userid)
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_edit->{
                val fragment:EditProfileFragment = EditProfileFragment()
                replaceFrament(R.id.fr_profile_box,fragment,true)
            }
        }
    }
}