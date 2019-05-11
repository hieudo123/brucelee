package com.example.lifestyle.hope.Activity;

import android.content.Context
import android.content.Intent
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View;
import android.widget.*
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.R;
import com.example.lifestyle.hope.Views.News.CreateNewsActivity
import com.example.lifestyle.hope.Views.News.NewsFragment
import com.example.lifestyle.hope.Views.Users.ProfileActivity
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.support.design.internal.BottomNavigationItemView
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import com.example.lifestyle.hope.Fragment.ShowProfileFragment
import com.example.lifestyle.hope.Fragment.YourPageFragment
import com.example.lifestyle.hope.Views.Products.houses.HouseFragment
import com.example.lifestyle.hope.Views.Users.Login.LoginActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

import io.socket.client.IO
import io.socket.client.Socket
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


public class MainActivity : BaseActivity(), View.OnClickListener {


    lateinit var bottomnav: BottomNavigationView
    //Fragment
    private val fmNews = NewsFragment()
    private val fmHouse = HouseFragment()
    private val fmProfile = YourPageFragment()
    private val fmFurnite = ""
    private val fm = supportFragmentManager
    private var active: Fragment = fmNews
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        var drawerLayout: DrawerLayout
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        /* val fragment: NewsFragment = NewsFragment()
         replaceFrament(R.id.fr_container, fragment, false)*/
        hideLeftActionIcon(true)
        init()
        // fm.beginTransaction().add(R.id.fr_container,fmFurnite,"FmHouse").hide(fmHouse).commit()
        fm.beginTransaction().add(R.id.fr_container, fmHouse, "FmHouse").hide(fmHouse).commit()
        fm.beginTransaction().add(R.id.fr_container, fmProfile, "FmProfile").hide(fmProfile).commit()
        fm.beginTransaction().add(R.id.fr_container, fmNews, "FmNews").commit()

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }
    fun changeFragment(item: MenuItem) {
        when (item.itemId) {

            R.id.navigation_home -> {
                fm.beginTransaction().hide(active).show(fmNews).commit()
                active = fmNews
                /* fragment = NewsFragment()
                 replaceFrament(R.id.fr_container, fragment, false)*/
            }
            R.id.nav_house -> {
                fm.beginTransaction().hide(active).show(fmHouse).commit()
                active = fmHouse
                /*  fragment = HouseFragment()
                  replaceFrament(R.id.fr_container, fragment, false)*/
            }
            R.id.nav_furniture -> {

            }
            R.id.nav_profile -> {
                fm.beginTransaction().hide(active).show(fmProfile).commit()
                active = fmProfile
                /*   fragment = YourPageFragment()
                   replaceFrament(R.id.fr_container, fragment, false)*/
            }
        }
    }

    fun init() {
        bottomnav = findViewById(R.id.bottom_nav) as BottomNavigationView
        drawerLayout = findViewById(R.id.drawerlayout)

        bottomnav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                changeFragment(p0)
                return true
            }

        })
    }

    override fun onClick(v: View?) {

    }

    override fun onBackPressed() {

        if(fmNews.isVisible){
            super.onBackPressed()
        }
        else if(fmHouse.isVisible){
            fm.beginTransaction().hide(active).show(fmNews).commit()
            active = fmNews
            bottomnav.selectedItemId = R.id.navigation_home
        }
        else if(fmProfile.isVisible){
            fm.beginTransaction().hide(active).show(fmNews).commit()
            active = fmNews
            bottomnav.selectedItemId = R.id.navigation_home
        }
    }
}
