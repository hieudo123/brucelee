package com.example.lifestyle.hope.Views.News

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.lifestyle.hope.R
import java.io.InputStream
import java.util.*
import com.example.lifestyle.hope.Activity.BaseActivity


class CreateNewsActivity: BaseActivity(),View.OnClickListener {


    override fun getLayoutResourceId(): Int {
        return R.layout.activity_create_news

    }
    lateinit var createdTime:TextView
    lateinit var image:ImageView
    lateinit var createdBy:TextView
    lateinit var share:TextView
    var position:Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.createnew)
        anhXa()
    }
    fun anhXa()
    {
        createdTime = findViewById(R.id.tv_createat)
        createdBy = findViewById(R.id.tv_createdby)
        share = findViewById(R.id.tv_sharenews)
        image = findViewById(R.id.iv_news_image)


        var date = Date()
        var s:CharSequence = android.text.format.DateFormat.format("HH:mm:ss dd/MM/yyyy",date.time)
        createdTime.setText(s.toString())

        share.setOnClickListener(this)
        image.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_news_image->{
                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent,1)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data!= null)
        {
            var uri:Uri =data.data
            var inputStream:InputStream = getContentResolver().openInputStream(uri)
            var bitmap:Bitmap =BitmapFactory.decodeStream(inputStream)
            image.setImageBitmap(bitmap)

        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}