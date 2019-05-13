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
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.facebook.login.widget.ProfilePictureView
import com.google.android.gms.maps.*
import java.net.URL
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng




class ShowProfileFragment : BaseFragment(),View.OnClickListener, OnMapReadyCallback {

    lateinit var mMap : GoogleMap
    lateinit var avartar : ProfilePictureView
    var userid : String = ""
    lateinit var editProfile:TextView
    lateinit var sharePref:SharePref
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_show_profile,container,false)
        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.ll_mapBox,mapFragment).commit()
        mapFragment.getMapAsync(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    fun init(v:View){

    }
    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }
    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}