package com.example.lifestyle.hope.Views.Users.SiginUp


import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.presenter.Users.SiginUp.PreHandlerSigin
import com.google.android.gms.auth.TokenData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.core.AuthTokenProvider
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SiginUp : BaseActivity(),ViewHandlerSigin,View.OnClickListener{
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_siginup
    }
    private lateinit var auth: FirebaseAuth
    lateinit var emptyText : TextView
    lateinit var acceptPhone : TextView
    lateinit var progressBar: ProgressBar
    lateinit var container:RelativeLayout
    lateinit var sigin: TextView
    lateinit var name:EditText
    lateinit var phone:EditText
    lateinit var password:EditText
    lateinit var re_password:EditText
    lateinit var verificationId:String
    lateinit var tv_SMSCode :TextView
    lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var verifyBox :LinearLayout
    lateinit var verify :TextView
    var isAccept = false
    var re_send = false
    var smsCode : String  = " "
    lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          init()
          settingToolBar()
          auth = FirebaseAuth.getInstance()
    }
    fun settingToolBar(){
        var toolbar:android.support.v7.widget.Toolbar = findViewById(R.id.tb_toolbar)as Toolbar
        setTitle(R.string.sigin)
        setLeftActionIcon(R.drawable.ic_arrow_back_black_24dp)
        HideIconRight(true)
    }

    fun init(){
        container = findViewById(R.id.container)
        sigin = findViewById(R.id.tv_sigin_up)
        name = findViewById(R.id.et_name)
        password = findViewById(R.id.et_password)
        re_password= findViewById(R.id.et_re_password)
        phone = findViewById(R.id.et_phone)
        progressBar = findViewById(R.id.progressBar)
        emptyText = findViewById(R.id.tv_error)
        acceptPhone = findViewById(R.id.tv_accept)
        tv_SMSCode = findViewById(R.id.tv_smsCode)
        verifyBox = findViewById(R.id.ll_verifyBox)
        verify = findViewById(R.id.tv_verify)

        verify.setOnClickListener(this)
        sigin.setOnClickListener(this)
        acceptPhone.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_sigin_up -> {

                if(re_password.text.isNotEmpty() && password.text.isNotEmpty() && name.text.isNotEmpty() && phone.text.isNotEmpty()){
                    onProgress()
                    Sigin(name.text.toString().trim(),
                            password.text.toString().trim(),
                            re_password.text.toString().trim(),
                            phone.text.toString().trim())
                }
                else
                    emptyText.visibility = View.VISIBLE
            }
            R.id.tv_accept->{
                if(phone.text.isNotEmpty()){
                    onProgress()
                    val phoneNumber : String = "+84"+ phone.text.toString().trim()
                    sendVerificationCode(phoneNumber)
                    if(isAccept)
                        reSendVerificationCode(phoneNumber)
                    else{
                        sendVerificationCode(phoneNumber)
                        acceptPhone.setText(getString(R.string.re_send))
                    }

                }
            }
            R.id.tv_verify->{
                onProgress()
                verifyCode(smsCode)
            }
        }
    }
    fun Sigin(name:String , password:String ,re_password:String , phone:String) {
           if (re_password == password){
               var preHandlerSigin: PreHandlerSigin
               preHandlerSigin = PreHandlerSigin(this, this)
               preHandlerSigin.Sigin(name, password, phone)

           }
           else
               Toast.makeText(this,"Mật khẩu xác thực không trùng !",Toast.LENGTH_SHORT).show()
    }
    fun onProgress(){
        progressBar.visibility = View.VISIBLE
    }
    override fun SiginOnSuccess(mess: String) {
       Toast.makeText(this,mess,Toast.LENGTH_SHORT).show()
        name.text.clear()
        phone.text.clear()
        password.text.clear()
        re_password.text.clear()
        progressBar.visibility = View.GONE
        emptyText.visibility = View.GONE
        onBackPressed()
    }

    override fun SiginOnFail(mess: String) {
        Toast.makeText(this,mess,Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE
    }


    fun reSendVerificationCode(phoneNumber : String){
        setUpVerificationCallback()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                30,
                TimeUnit.SECONDS,
                this,
                callbacks,
                resendToken)
    }
    fun sendVerificationCode(phoneNumber : String){
        setUpVerificationCallback()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                30,
                TimeUnit.SECONDS,
                this,
                callbacks)
    }
    fun setUpVerificationCallback(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
                Log.e("ZZZ1",p0.toString())
                progressBar.visibility = View.GONE
                if (p0 != null) {
                    smsCode = p0.smsCode.toString()
                    tv_SMSCode.setText(smsCode)
                }
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String?) {
                super.onCodeAutoRetrievalTimeOut(p0)
                verificationId = p0.toString()
                Log.e("ZZZ2",p0.toString())
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                Log.e("ZZZ3",p0.toString())
                progressBar.visibility = View.GONE
            }
            override fun onCodeSent(p0: String?,p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                progressBar.visibility = View.GONE
                verificationId = p0.toString()
                resendToken = p1
            }
        }
    }
    fun verifyCode(code: String){
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)

    }
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressBar.visibility = View.GONE
                        Log.d("AAA", "signInWithCredential:success")
                    } else {
                        Log.w("BBB", "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
