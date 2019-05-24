package com.example.lifestyle.hope.Views.News

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.*
import com.esafirm.imagepicker.features.ImagePicker
import com.example.lifestyle.hope.R
import java.io.InputStream
import java.util.*
import com.example.lifestyle.hope.Activity.BaseActivity
import com.example.lifestyle.hope.Adapter.ImageAdapter
import com.example.lifestyle.hope.Adapter.NewsAdapter
import com.example.lifestyle.hope.Models.BaseModels
import com.example.lifestyle.hope.Models.News
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.Views.UpLoadToFireBase
import com.example.lifestyle.hope.presenter.News.CreateNews.PreHandlerCreateNews
import com.example.lifestyle.hope.presenter.News.CreateNews.PreHandlerCreateNewsV2
import com.example.lifestyle.hope.respone.test
import com.example.lifestyle.hope.retrofit.ApiService
import com.example.lifestyle.hope.retrofit.Config
import com.example.lifestyle.hope.retrofit.RetrofitClient
import com.example.lifestyle.hope.utils.SharePref
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.collections.ArrayList


class CreateNewsActivity: BaseActivity(),View.OnClickListener,ViewHandlerCreateNews,UpLoadToFireBase {

    var storage = FirebaseStorage.getInstance("gs://hope-1557133861463.appspot.com")
    lateinit var picker: FloatingActionButton
    lateinit var users: Users
    lateinit var errorNotifi : TextView
    lateinit  var progressBar: ProgressBar
    lateinit var listPicker : RecyclerView
    lateinit var newsTitle: TextView
    lateinit var bodyNews: TextView
    lateinit var news : News
    lateinit var image:ImageView
    lateinit var share:TextView
    var position:Int =0
    lateinit var calendar : Calendar
    lateinit var adapter : ImageAdapter
    val storageRef = storage.reference
    lateinit var preHandlerCreateNews: PreHandlerCreateNews
    lateinit var preHandlerCreateNewsV2: PreHandlerCreateNewsV2
    lateinit var imageList : ArrayList<String>
    lateinit var imageUrl  :ArrayList<String>
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
        imageUrl = ArrayList()
        imageList = ArrayList()
        picker = findViewById(R.id.fbtn_picker)
        newsTitle = findViewById(R.id.et_title)
        bodyNews = findViewById(R.id.et_content)
        share = findViewById(R.id.tv_sharenews)
        image = findViewById(R.id.iv_news_image)
        errorNotifi = findViewById(R.id.tv_error)
        progressBar = findViewById(R.id.progress)
        listPicker = findViewById(R.id.rv_image)
        share.setOnClickListener(this)
        picker.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.fbtn_picker->{
//                var intent = Intent(Intent.ACTION_PICK)
//                intent.setType("image/*")
//                startActivityForResult(intent,1)
                ImagePicker.create(this) // Activity or Fragment
                        .start();

            }
            R.id.tv_sharenews->{
                if(newsTitle.text.isNotEmpty() && bodyNews.text.isNotEmpty()){
                    uploadFromLocalFile()
                }
                else{
                    errorNotifi.visibility = View.VISIBLE
                    errorNotifi.setText(getString(R.string.empty))
                }
            }
        }
    }
    fun setAdapter(){
        adapter = ImageAdapter(imageList)
        var GridLayoutManager :GridLayoutManager = GridLayoutManager(this,3)
        listPicker.layoutManager = GridLayoutManager
        listPicker.adapter= adapter
    }
    fun getData(){
        val sharePref = SharePref(this)
        if(sharePref.user != null){
            users = sharePref.user
        }
    }
    //chỉ upload 1 hình duy nhất
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
                    imageUrl.add(downloadUri.toString())
                    postNews()
                } else {
                    createOnFail()
                }
            }
        }
    }
    //upload 1 cái mảng nhiều hình lên firebase
    fun uploadFromLocalFile(){
       firebaseInProgress()
       for(i: Int in 0..imageList.size-1){
           var file = Uri.fromFile(File(imageList[i]))
           val riversRef = storageRef.child("images/${file.lastPathSegment.replace(" ","")}")
           val uploadTask:UploadTask
           uploadTask = riversRef.putFile(file)
           uploadTask.addOnFailureListener {
           }.addOnSuccessListener {
               val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                   if (!task.isSuccessful) {
                       task.exception?.let {
                           throw it
                       }
                   }
                   return@Continuation riversRef.downloadUrl
               }).addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       var downloadUri = task.result
                       Log.e("LLN",downloadUri.toString())
                       imageUrl.add(downloadUri.toString())
                       firebaseOnSuccess()
                   } else {
                       firebaseOnFail()
                   }
               }
           }
       }
    }
    fun postNews(){
            preHandlerCreateNews = PreHandlerCreateNews(this, this)
            preHandlerCreateNews.createNews(newsTitle.text.toString(),
                    bodyNews.text.toString(),users.username,calendar.timeInMillis,imageUrl[0])
    }
//    fun test(){
//        calendar  = Calendar.getInstance()
//        val retrofit = RetrofitClient.getClient(Config.URL)
//        val apiService = retrofit!!.create(ApiService::class.java)
//        val call : Call<BaseModels> = apiService.test(imageUrl,newsTitle.text.toString(),
//                bodyNews.text.toString(),users.id,calendar.timeInMillis)
//        call.enqueue(object : Callback<BaseModels>{
//            override fun onFailure(call: Call<BaseModels>?, t: Throwable?) {
//                Log.e("LLL",t.toString())
//            }
//
//            override fun onResponse(call: Call<BaseModels>?, response: Response<BaseModels>?) {
//                if(response!= null){
//                    val jsonRequest = response.body()
//                    Log.e("LLL",response.body().success.toString())
//                }
//            }
//        })
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data!= null)
//        {
//            var uri:Uri =data.data
//            var inputStream:InputStream = getContentResolver().openInputStream(uri)
//            var bitmap:Bitmap =BitmapFactory.decodeStream(inputStream)
//            image.setImageBitmap(bitmap)
//
//        }
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            for (i: Int in 0..ImagePicker.getImages(data).size-1) {
                imageList.add(ImagePicker.getImages(data)[i].path)
            }
            setAdapter()
            Log.e("IMAGE",imageList.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = this.contentResolver?.query(contentUri,
                null,
                null,
                null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        if (cursor != null) {
            cursor.moveToFirst()
        }
        Log.e("FULLNAME", cursor!!.getString(column_index!!))
        return cursor!!.getString(column_index!!)
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
    override fun firebaseInProgress() {
        progressBar.visibility = View.VISIBLE
        errorNotifi.visibility = View.GONE
    }

    override fun firebaseOnSuccess() {
        if(imageUrl.size == imageList.size){
            calendar  = Calendar.getInstance()
            preHandlerCreateNewsV2 = PreHandlerCreateNewsV2(this)
            preHandlerCreateNewsV2.createNews(newsTitle.text.toString(),
                    bodyNews.text.toString(),users.id,calendar.timeInMillis,imageUrl)
        }
        progressBar.visibility = View.GONE
        errorNotifi.visibility = View.GONE
    }

    override fun firebaseOnFail() {
        progressBar.visibility = View.GONE
        Toast.makeText(this,"Đăng thất bại !",Toast.LENGTH_SHORT).show()
        errorNotifi.visibility = View.GONE
    }
}