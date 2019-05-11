package com.example.lifestyle.hope.Fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.LinkAddress
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.lifestyle.hope.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.InputStream
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.utils.SharePref
import com.google.android.gms.common.api.internal.TaskUtil
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.net.URI
import java.util.*


class EditProfileFragment:BaseFragment(),View.OnClickListener {
    lateinit var user : Users
    lateinit var avatar :CircleImageView
    lateinit var editAvatar : ImageView
    var userid : String = ""
    lateinit var savePhoto : ImageView
    lateinit var phonNumber : EditText
    lateinit var email:EditText
    lateinit var gender : EditText
    lateinit var address: EditText
    lateinit var sharePref :SharePref
    lateinit var username : EditText
    lateinit var takePhoto : ImageView
    var REQUESTCODE: Int = 0
    var storage = FirebaseStorage.getInstance("gs://hope-1557133861463.appspot.com")
    val storageRef = storage.reference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View = inflater.inflate(R.layout.fragment_edit_profile,container,false)
        sharePref = SharePref(context)


        init(view)
        getData()
        return view
    }
    fun init(v : View){

        avatar = v.findViewById(R.id.iv_avartar)
        editAvatar = v.findViewById(R.id.iv_edit_picture)
        username = v.findViewById(R.id.et_username)
        phonNumber = v.findViewById(R.id.et_phone_pr)
        address = v.findViewById(R.id.et_address_pr)
        email = v.findViewById(R.id.et_email_pr)
        gender = v.findViewById(R.id.et_gioitinh_pr)
        takePhoto = v.findViewById(R.id.iv_take_photo)
        savePhoto = v.findViewById(R.id.iv_save_photo)

        takePhoto.setOnClickListener(this)
        avatar.setOnClickListener(this)
        editAvatar.setOnClickListener(this)
        savePhoto.setOnClickListener(this)


//        userid = sharePref.getString("userid","")
//        if(userid != null){
//            Log.e("AAA",userid.replace(" ",""))
//            userid = userid.trim()
//            Picasso.get().load("https://graph.facebook.com/" + userid+ "/picture?type=large").into(avatar)
//        }

    }
    override fun onClick(v: View?) {
       when(v?.id){
           R.id.iv_take_photo->{
               val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
               startActivityForResult(intent,1)
           }
           R.id.iv_edit_picture->{
               var intent = Intent(Intent.ACTION_PICK)
               intent.setType("image/*")
               startActivityForResult(intent,2)

           }
           R.id.iv_save_photo->{
                upLoadImage()
           }
       }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && data!= null)
        {
            when (requestCode){
                1->{
                    val bitmap: Bitmap = data.extras.get("data") as Bitmap
                    avatar.setImageBitmap(bitmap)
                }
                2->{
                    var uri: Uri =data.data
                    var inputStream: InputStream = context?.contentResolver!!.openInputStream(uri)
                    var bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    avatar.setImageBitmap(bitmap)
                    getRealPathFromURI(uri)
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
    fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = context?.contentResolver?.query(contentUri,
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
    fun getData(){
        val sharePref = SharePref(context)
        if(sharePref.user != null){
            user = sharePref.user
            username.setText(user.username)
            if(user.image != ""){
                Picasso.get().load(user.image).into(avatar)
            }

        }
    }
    fun upLoadImage(){
        avatar.isDrawingCacheEnabled = true
        avatar.buildDrawingCache()
        val bitmap = (avatar.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val calendar : Calendar = Calendar.getInstance()
        val mountainsRef = storageRef.child("Image.png")
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
           Toast.makeText(context,"Up load fail",Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Toast.makeText(context, "Up was Successed", Toast.LENGTH_SHORT).show()
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
                    Log.e("LLL",downloadUri.toString())
                } else {

                }
            }
        }

    }

}