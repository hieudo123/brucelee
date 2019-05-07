package com.example.lifestyle.hope.Views.Users.SiginUp


import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.presenter.Users.SiginUp.PreHandlerSigin

class SiginUp : BaseActivity(),ViewHandlerSigin,View.OnClickListener{
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_siginup
    }
    lateinit var progressBar: ProgressBar
    lateinit var container:LinearLayout
    lateinit var logo:ImageView
    lateinit var sigin: TextView
    lateinit var name:EditText
    lateinit var phone:EditText
    lateinit var password:EditText
    lateinit var re_password:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       init()
        startAnimation()
        val handler = Handler()
        handler.postDelayed({
            container.visibility = View.VISIBLE
            logo.visibility = View.GONE
        }, 1200)
        var toolbar:android.support.v7.widget.Toolbar = findViewById(R.id.tb_toolbar)as Toolbar
        toolbar.setBackgroundResource(R.color.color_siginbar)
        setTitle(R.string.sigin)
        setLeftActionIcon(R.drawable.ic_arrow_back_black_24dp)
        HideIconRight(true)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.tv_sigin_up -> {
                progressBar.visibility = View.VISIBLE
                Sigin(name.text.toString().trim(),
                        password.text.toString().trim(),
                        re_password.text.toString().trim(),
                        phone.text.toString().trim())
            }
        }
    }

    fun init(){
        container = findViewById(R.id.container)
        sigin = findViewById(R.id.tv_sigin_up)
        logo = findViewById(R.id.iv_logo)
        name = findViewById(R.id.et_name)
        password = findViewById(R.id.et_password)
        re_password= findViewById(R.id.et_re_password)
        phone = findViewById(R.id.et_phone)
        progressBar = findViewById(R.id.progressBar)
        sigin.setOnClickListener(this)
    }

    fun startAnimation() {
        var anime = AnimationUtils.loadAnimation(this, R.anim.upto2)
        logo.startAnimation(anime)
    }

    fun Sigin(name:String , password:String ,re_password:String , phone:String) {
       if(re_password == password) {
           var preHandlerSigin: PreHandlerSigin
           preHandlerSigin = PreHandlerSigin(this, this)
           preHandlerSigin.Sigin(name, password, phone)
       }
       else {
           Toast.makeText(this,"Mật khẩu xác thực không trùng !",Toast.LENGTH_SHORT).show()
       }
    }
    override fun SiginOnSuccess(mess: String) {
       Toast.makeText(this,mess,Toast.LENGTH_SHORT).show()
        name.text.clear()
        phone.text.clear()
        password.text.clear()
        re_password.text.clear()
        progressBar.visibility = View.GONE
        onBackPressed()
    }

    override fun SiginOnFail(mess: String) {
        Toast.makeText(this,mess,Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
