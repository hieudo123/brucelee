package com.example.lifestyle.hope.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.example.lifestyle.hope.R
import com.example.lifestyle.hope.utils.SharePref
import com.github.ybq.android.spinkit.style.WanderingCubes

class WaitingActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_waiting)
        super.onCreate(savedInstanceState)
        val progressBar:ProgressBar
        progressBar = findViewById(R.id.progress)
        val doubleBounce = WanderingCubes()
        progressBar.indeterminateDrawable = doubleBounce
        val handler = Handler()

        handler.postDelayed({
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}