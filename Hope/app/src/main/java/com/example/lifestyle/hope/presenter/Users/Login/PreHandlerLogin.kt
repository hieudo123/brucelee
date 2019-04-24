package com.example.lifestyle.hope.presenter.Users.Login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.Views.Users.Login.ViewHandlerLogin
import org.json.JSONObject

class PreHandlerLogin(var context: Context, var user: Users?, var v: ViewHandlerLogin) : PreImpLogin {

    override fun getLogin(phone: String, password: String) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        var url: String = "http://192.168.10.164/hope/Login.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Log.e("GetString", response.toString())
                    var objects = JSONObject(response.toString())
                    if (objects.getInt("Success") == 1) {
                        var mess = objects.getString("Message")
                        var u =objects.getJSONObject("data")
                        user = Users(u.getInt("Id"),
                                u.getString("Username"),
                                u.getString("Password"),
                                u.getString("Phone"),
                                u.getString("Address"),
                                u.getString("Email"),
                                u.getString("Image"),
                                u.getInt("Status"))
                        Log.e("Object", user!!.username)
                        v.LoginOnSuccess(mess)
                    } else {
                        var mess = objects.getString("Message")
                        v.LoginOnFail(mess)

                    }
                }, Response.ErrorListener { error ->
            Log.e("Volley Error", error.toString())
            v.LoginOnFail("Đăng nhập thất bại ..!")
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("Phone", phone)
                params.put("Password", password)
                return params
            }
        }
        requestQueue.add(stringRequest)
    }
}


