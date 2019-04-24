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
    lateinit var image1:ImageView
    lateinit var image2:ImageView
    lateinit var pickImage:FloatingActionButton
    lateinit var createdBy:TextView
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
        pickImage = findViewById(R.id.fbtn_addimage)
        image = findViewById(R.id.iv_news_image)
        image1 = findViewById(R.id.iv_news_image1)
        image2 = findViewById(R.id.iv_news_image2)

        var date = Date()
        var s:CharSequence = android.text.format.DateFormat.format("HH:mm:ss dd/MM/yyyy",date.time)
        createdTime.setText("Created at : "+ s)

        pickImage.setOnClickListener(this)
        image.setOnClickListener(this)
        image1.setOnClickListener(this)
        image2.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.fbtn_addimage ->{
                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent,1)
            }
            R.id.iv_news_image->{
                position = 0;
                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent,1)

            }
            R.id.iv_news_image1->{
                position = 1;
                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent,1)

            }
            R.id.iv_news_image2->{
                position = 2;
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

            when(position)
            {
                0->image.setImageBitmap(bitmap)
                1->image1.setImageBitmap(bitmap)
                2->image2.setImageBitmap(bitmap)
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}