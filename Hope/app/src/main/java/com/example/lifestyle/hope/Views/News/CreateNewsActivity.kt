package com.example.lifestyle.hope.Views.News

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View
import android.widget.*
import com.example.lifestyle.hope.R
import java.io.InputStream
import java.util.*
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.presenter.News.CreateNews.PreHandlerCreateNews
import com.example.lifestyle.hope.utils.SharePref
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream


class CreateNewsActivity: BaseActivity(),View.OnClickListener,ViewHandlerCreateNews {
    lateinit var picker: FloatingActionButton
    lateinit var users: Users
    lateinit var errorNotifi : TextView
    lateinit  var progressBar: ProgressBar
    var imageUrl = ""
    lateinit var newsTitle: TextView
    lateinit var bodyNews: TextView
    lateinit var news : News
    lateinit var image:ImageView
    lateinit var createdBy:TextView
    lateinit var share:TextView
    var position:Int =0
    lateinit var calendar : Calendar
    var storage = FirebaseStorage.getInstance("gs://hope-1557133861463.appspot.com")
    val storageRef = storage.reference
    lateinit var preHandlerCreateNews: PreHandlerCreateNews
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.createnew)
        init()
        getData()
    }
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_create_news

    }
    fun init()
    {
        picker = findViewById(R.id.fbtn_picker)
        newsTitle = findViewById(R.id.et_title)
        bodyNews = findViewById(R.id.et_content)
        createdBy = findViewById(R.id.tv_createdby)
        share = findViewById(R.id.tv_sharenews)
        image = findViewById(R.id.iv_news_image)
        errorNotifi = findViewById(R.id.tv_error)
        progressBar = findViewById(R.id.progress)
        share.setOnClickListener(this)
        picker.setOnClickListener(this)
    }
    fun upLoadImage(){
        calendar  = Calendar.getInstance()
        createInProgress()
        image.isDrawingCacheEnabled = true
        image.buildDrawingCache()
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val mountainsRef = storageRef.child("NewsPicture/Image"+calendar.timeInMillis+".png")
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener {

            val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation mountainsRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.e("LLN",downloadUri.toString())
                    imageUrl = downloadUri.toString()
                    postNews()
                } else {
                    createOnFail()
                }
            }
        }
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.fbtn_picker->{
                var intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent,1)

            }
            R.id.tv_sharenews->{
                if(newsTitle.text.isNotEmpty() && bodyNews.text.isNotEmpty()){
                    upLoadImage()
                }
                else{
                    errorNotifi.visibility = View.VISIBLE
                    errorNotifi.setText(getString(R.string.empty))
                }


            }
        }
    }
    fun postNews(){
            preHandlerCreateNews = PreHandlerCreateNews(this, this)
            preHandlerCreateNews.createNews(newsTitle.text.toString(),
                    bodyNews.text.toString(),users.username,calendar.timeInMillis,imageUrl)
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
    override fun createInProgress() {
        progressBar.visibility = View.VISIBLE
        errorNotifi.visibility = View.GONE
    }

    override fun createOnSuccess() {
        progressBar.visibility = View.GONE
        Toast.makeText(this, "Đăng thành công !", Toast.LENGTH_SHORT).show()
        errorNotifi.visibility = View.GONE
    }

    override fun createOnFail() {
        progressBar.visibility = View.GONE
        Toast.makeText(this,"Đăng thất bại !",Toast.LENGTH_SHORT).show()
        errorNotifi.visibility = View.GONE
    }
    fun getData(){
        val sharePref = SharePref(this)
        if(sharePref.user != null){
            users = sharePref.user
            createdBy.setText(users.username)
        }
    }
}