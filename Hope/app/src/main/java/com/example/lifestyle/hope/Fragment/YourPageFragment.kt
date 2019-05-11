package com.example.lifestyle.hope.Fragment

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.annotation.NonNull
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ShareActionProvider
import android.widget.TextView
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.Users.Login.LoginActivity
import com.example.lifestyle.hope.Views.Users.ProfileActivity
import com.example.lifestyle.hope.utils.SharePref
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.github.ybq.android.spinkit.style.Circle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject

class YourPageFragment : BaseFragment(),View.OnClickListener{
    lateinit var avatar : CircleImageView
    lateinit var user : Users
    lateinit var logout:TextView
    lateinit var showProfile:TextView
    lateinit var username:TextView
    var userid : String = ""
    lateinit var sharePref : SharePref
    lateinit var mGoogleSignInClient : GoogleSignInClient
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_your_page,container,false)
        sharePref = SharePref(context)
        init(view)
        getData()
        configureGoogle()
        return view
    }
    fun configureGoogle(){
        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it,gso) }!!
    }
    fun init(v:View) {
        logout = v.findViewById(R.id.tv_logout)
        showProfile = v.findViewById(R.id.tv_your_profile)
        username = v.findViewById(R.id.tv_username)
        avatar = v.findViewById(R.id.iv_avt)

        showProfile.setOnClickListener(this)
        logout.setOnClickListener(this)



        userid = sharePref.getString("userid","")
        if(userid != null){
            Log.e("AAA",userid.replace(" ",""))
            userid = userid.trim()
            Picasso.get().load("https://graph.facebook.com/" + userid+ "/picture?type=large").into(avatar)
        }

    }
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.tv_logout->{
                logOut()
            }
            R.id.tv_your_profile->
            {
                val intent = Intent(context,ProfileActivity::class.java)
                startActivity(intent)
            }
        }

    }
    fun getData(){
        val sharePref = SharePref(context)
        if(sharePref.user != null){
            user = sharePref.user
            username.setText(user.username)
            if(user.image != ""){
                Picasso.get().load(user.image).error(R.drawable.ic_account_circle_black_24dp).into(avatar)
            }

        }
    }
    fun logOut(){
        LoginManager.getInstance().logOut()

        sharePref.clear()

        mGoogleSignInClient.signOut().addOnCompleteListener(object : OnCompleteListener<Void>{
            override fun onComplete(p0: Task<Void>) {

            }
        })
        changeActivity()
    }
    fun changeActivity(){
        val intent = Intent(context,LoginActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }
}