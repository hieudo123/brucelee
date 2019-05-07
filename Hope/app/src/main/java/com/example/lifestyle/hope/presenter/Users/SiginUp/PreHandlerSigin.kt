package com.example.lifestyle.hope.presenter.Users.SiginUp

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lifestyle.hope.Views.Users.SiginUp.ViewHandlerSigin
import org.json.JSONObject

public class PreHandlerSigin(var context: Context,var v:ViewHandlerSigin):PreImpSigin{
    override fun Sigin(name: String, password: String, phone: String) {
        val requestQueue:RequestQueue= Volley.newRequestQueue(context)
        val url:String ="https://androidwebsv.000webhostapp.com/hope/Siginup.php"
        val stringRequest = object : StringRequest(Request.Method.POST,url,
                Response.Listener {response ->
                    Log.e("GetString", response.toString())
                    if(response.toString() !=null)
                    {
                        var objects = JSONObject(response.toString())
                        if (objects.getInt("Success") == 1) {
                            var mess = objects.getString("Message")
                            v.SiginOnSuccess(mess)
                        } else {
                            var mess = objects.getString("Message")
                            v.SiginOnFail(mess)

                        }
                    }


                },
                Response.ErrorListener { v.SiginOnFail("Dăng ký thất bại !")  })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params.put("name",name)
                params.put("Phone",phone)
                params.put("Password",password)

                return params
            }
        }
        requestQueue.add(stringRequest)

    }
}