package com.example.lifestyle.hope.Views.Users.Login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.lifestyle.hope.Activity.WaitingActivity
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.Users.SiginUp.SiginUp
import com.example.lifestyle.hope.presenter.Users.Login.PreHandlerLogin
import android.widget.ProgressBar
import com.example.lifestyle.hope.Fragment.CautionDialogFragment
import com.example.lifestyle.hope.Fragment.ProgressDialogFragment
import com.example.lifestyle.hope.Models.FacebookLogin
import com.example.lifestyle.hope.utils.SharePref
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*


class LoginActivity : AppCompatActivity(), ViewHandlerLogin, View.OnClickListener {
    var  progressDialogFragment : ProgressDialogFragment = ProgressDialogFragment()
    lateinit var callbackManager: CallbackManager
    lateinit var loginfb:TextView
    lateinit var progressBar:ProgressBar
    lateinit var loginbox: RelativeLayout
    lateinit var phone: EditText
    lateinit var password: EditText
    lateinit var login: Button
    lateinit var sigin: Button
    lateinit var siginGoogle: TextView
    lateinit var preHandlerLogin: PreHandlerLogin
    lateinit var showPassword:TextView
    lateinit var sharePref :SharePref
    lateinit var mGoogleSignInClient : GoogleSignInClient
    lateinit var faceBookLogin : FacebookLogin
    var RC_SIGN_IN = 1
    var user: Users? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        sharePref = SharePref(this)
        init()
        FacebookSdk.sdkInitialize(this)
        callbackManager = CallbackManager.Factory.create()
        faceBookLogin = FacebookLogin(this,this , callbackManager)
        configureGoogle()
        startAnimation()
        autoLogin()
    }

    fun configureGoogle(){
        val gso : GoogleSignInOptions  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
    }
    fun onTextChange(item :EditText){
        item.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!!.count() ==0){
                    showPassword.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {
                    showPassword.visibility = View.VISIBLE
                }
            }
        })
    }
    fun init() {
        showPassword = findViewById(R.id.tv_show_pass)
        loginbox = findViewById(R.id.bg_bottom)
        phone = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)
        login = findViewById(R.id.btn_login)
        sigin = findViewById(R.id.btn_sigin)
        loginfb =findViewById( R.id.tv_login_fb)
        progressBar = findViewById(R.id.progress) as ProgressBar
        siginGoogle = findViewById(R.id.tv_sigin_google)


        login.setOnClickListener(this)
        sigin.setOnClickListener(this)
        loginfb.setOnClickListener(this)
        showPassword.setOnClickListener(this)
        siginGoogle.setOnClickListener(this)
        onTextChange(password)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                Login(phone.text.toString(), password.text.toString())
                showProgressDialog(true)
            }
            R.id.btn_sigin -> {
                val intent = Intent(this,SiginUp::class.java)
                startActivity(intent)
            }
            R.id.tv_login_fb->{
                LoginManager.getInstance().logInWithReadPermissions(
                        this,
                        Arrays.asList("user_birthday", "user_location", "email"))
                faceBookLogin.loginFaceBook()
            }
            R.id.tv_show_pass->{
                if(password.transformationMethod == PasswordTransformationMethod.getInstance()) {
                    showPassword.setText(R.string.hide)
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                }
                else {
                    showPassword.setText(R.string.show)
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance())
                }
            }
            R.id.tv_sigin_google->{
                signIn()
            }
        }

    }

    fun startAnimation() {
        var anime = AnimationUtils.loadAnimation(this, R.anim.upto)
        loginbox.startAnimation(anime)
    }

    //Đăng nhập bằng tài khoản của app
    fun Login(phone: String, password: String) {
        preHandlerLogin = PreHandlerLogin(this, user, this);
        preHandlerLogin.getLogin(phone, password)

    }
    //Sign In Google
    fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    //Sign In Google onSuccess, this fun use get info of user
    fun handleSignInResult(task: com.google.android.gms.tasks.Task<GoogleSignInAccount>?) {
        try {
            val account = task?.getResult()
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            Log.e("PHOTO", acct!!.getPhotoUrl().toString())
            Log.e("DSN",acct.displayName)

            if(account != null){
                Log.e("HHH",acct.id)
                user = Users(1111, acct.displayName!!,
                        "",
                        "",
                        "",
                        acct.email!!,
                        acct.photoUrl.toString(),0,1)
                sharePref.putUser(user)
                LoginOnSuccess(getString(R.string.loginsuccess))
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
        }
    }
    fun showProgressDialog(isProgress:Boolean){

        if (isProgress){
            progressDialogFragment.show(supportFragmentManager,null)
            progressDialogFragment.isCancelable =false
        }
        else
            progressDialogFragment.dismiss()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: com.google.android.gms.tasks.Task<GoogleSignInAccount>? = GoogleSignIn.getSignedInAccountFromIntent(data)
            if(task!!.isSuccessful) {
                handleSignInResult(task)
            }
        }
    }
    //this fun use for check user logined app and auto start main activity when user start app
    fun autoLogin(){
        if(AccessToken.getCurrentAccessToken()!=null ||GoogleSignIn.getLastSignedInAccount(this)!= null
                ||sharePref.user != null){
            val intent = Intent(this,WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun LoginOnSuccess(mess: String) {
        showProgressDialog(false)
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, WaitingActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun LoginOnFail(mess: String) {
       showDialog(mess)
        showProgressDialog(false)

    }
    fun showDialog(mess:String){
        var dialogLoginFragment: CautionDialogFragment = CautionDialogFragment()
        val args  = Bundle()
        args.putString("title",mess)
        dialogLoginFragment.arguments = args
        dialogLoginFragment.show(supportFragmentManager,null)
        dialogLoginFragment.isCancelable =false
    }
}