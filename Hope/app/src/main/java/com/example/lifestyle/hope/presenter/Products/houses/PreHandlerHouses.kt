package com.example.stackexchange.stackexchangeclone.presenter.House

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lifestyle.hope.Models.House
import org.json.JSONObject

class PreHandlerHouses(var houseList:ArrayList<House>, var context: Context):
    PreImpShowHouses {
    override fun getAllHouse() {
        val request = Volley.newRequestQueue(context);
        var url: String = "http://192.168.137.1/android/uploaddata.php"
        val stringReques = object : StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
            if (response.toString() != null) {
                var jsonObject = JSONObject(response)
                var jsonArray = jsonObject.getJSONArray("data")
                if (jsonArray.length()>0)
                {
                    Log.e("data", response.toString())
                    for (i:Int in 0 until  jsonArray.length()){
                        jsonObject= jsonArray.getJSONObject(i)
                        houseList.add(House(jsonObject.getInt("Id"),
                            jsonObject.getString("TenSP"),
                            jsonObject.getInt("Gia"),
                            jsonObject.getString("Hinh")))
                    }
                }
                else{
                    Log.e("data2", response.toString())
                }
            }
        }, Response.ErrorListener {error -> VolleyLog.e("Volley Error",error.toString())}){}
        request.add(stringReques);
    }

}