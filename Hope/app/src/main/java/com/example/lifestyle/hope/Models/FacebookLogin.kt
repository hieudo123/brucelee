package com.example.lifestyle.hope.Models

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.Views.Users.Login.ViewHandlerLogin
import com.example.lifestyle.hope.presenter.Users.Login.PreImpLogin
import com.example.lifestyle.hope.utils.SharePref
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONObject

class FacebookLogin( var v: ViewHandlerLogin,var context: Context , var callbackManager: CallbackManager){

    fun loginFaceBook() {
        Log.e("LGDB", "tesst")
        LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                v.LoginOnSuccess(context.getString(R.string.loginsuccess))
                Toast.makeText(context, "isFacebookLogin",Toast.LENGTH_SHORT).show()
                resultLoginFB()
            }
            override fun onCancel() {
                Toast.makeText(context, "isCancleFacebookLogin",Toast.LENGTH_SHORT).show()
            }
            override fun onError(error: FacebookException?) {
                Toast.makeText(context, "isNotFacebookLogin",Toast.LENGTH_SHORT).show()
                v.LoginOnFail(context.getString(R.string.loginfail))
            }
        })
    }
    fun resultLoginFB() {
        var graphRequest:GraphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                object :GraphRequest.GraphJSONObjectCallback{
                    override fun onCompleted(data: JSONObject?, response: GraphResponse?) {
                        if (response != null) {
                            Log.d("LoginFB",response.jsonObject.toString())
                            val sharePref = SharePref(context)
                            sharePref.putString("userid",response.jsonObject.getString("id"))
                        }
                    }
                })
        val parameters = Bundle()
        parameters.putString("fields","id,name,birthday")
        graphRequest.setParameters(parameters)
        graphRequest .executeAsync()
    }
}