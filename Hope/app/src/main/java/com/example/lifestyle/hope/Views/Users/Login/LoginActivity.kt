package com.example.lifestyle.hope.Views.Users.Login

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.lifestyle.hope.Activity.WaitingActivity
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.Users.SiginUp.SiginUp
import com.example.lifestyle.hope.presenter.Users.Login.PreHandlerLogin
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.sprite.Sprite
import android.widget.ProgressBar
import com.github.ybq.android.spinkit.style.WanderingCubes


class LoginActivity : AppCompatActivity(), ViewHandlerLogin, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                Login(phone.text.toString(), password.text.toString())
                progressBar.visibility = View.VISIBLE
//                val doubleBounce = WanderingCubes()
//                progressBar.indeterminateDrawable = doubleBounce

            }
            R.id.btn_sigin -> {
                //
                val intent = Intent(this,SiginUp::class.java)
                startActivity(intent)
            }
        }

    }

    lateinit var progressBar:ProgressBar
    lateinit var loginbox: RelativeLayout
    lateinit var phone: EditText
    lateinit var password: EditText
    lateinit var login: Button
    lateinit var sigin: Button
    lateinit var preHandlerLogin: PreHandlerLogin
    var user: Users? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        anhXa()
        startAnimation()

    }

    fun anhXa() {
        loginbox = findViewById(R.id.bg_bottom)
        phone = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)
        login = findViewById(R.id.btn_login)
        sigin = findViewById(R.id.btn_sigin)
        login.setOnClickListener(this)
        sigin.setOnClickListener(this)
        progressBar = findViewById(R.id.progress) as ProgressBar
    }

    fun startAnimation() {
        var anime = AnimationUtils.loadAnimation(this, R.anim.upto)
        loginbox.startAnimation(anime)
    }

    fun Login(phone: String, password: String) {
        preHandlerLogin = PreHandlerLogin(this, user, this);
        preHandlerLogin.getLogin(phone, password)

    }

    override fun LoginOnSuccess(mess: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, WaitingActivity::class.java)
        startActivity(intent)
    }

    override fun LoginOnFail(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE


    }
}