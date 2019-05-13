package com.example.lifestyle.hope.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifestyle.hope.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.InputStream
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.example.lifestyle.hope.Models.Users
import com.example.lifestyle.hope.Views.Users.UpdateProfile.ViewHandlerUpdateProfile
import com.example.lifestyle.hope.presenter.Users.PreHandlerUpdateProfile
import com.example.lifestyle.hope.utils.SharePref
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.*
import com.github.ybq.android.spinkit.style.Circle


class EditProfileFragment:BaseFragment(),View.OnClickListener,ViewHandlerUpdateProfile {
    lateinit var isEmptyForm: TextView
    lateinit var progressBar: ProgressBar
    lateinit var user : Users
    lateinit var avatar :CircleImageView
    lateinit var editAvatar : ImageView
    var userid : String = ""
    lateinit var save : Button
    lateinit var savePhoto : ImageView
    lateinit var phonNumber : EditText
    lateinit var email:EditText
    lateinit var gender : EditText
    lateinit var address: EditText
    lateinit var sharePref :SharePref
    lateinit var username : EditText
    lateinit var takePhoto : ImageView
    lateinit var preHandlerUpdateProfile: PreHandlerUpdateProfile
    var REQUESTCODE: Int = 0
    var imageUrl =""
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
        isEmptyForm = v.findViewById(R.id.tv_error)
        avatar = v.findViewById(R.id.iv_avartar)
        editAvatar = v.findViewById(R.id.iv_edit_picture)
        username = v.findViewById(R.id.et_username)
        phonNumber = v.findViewById(R.id.et_phone_pr)
        address = v.findViewById(R.id.et_address_pr)
        email = v.findViewById(R.id.et_email_pr)
        gender = v.findViewById(R.id.et_gioitinh_pr)
        takePhoto = v.findViewById(R.id.iv_take_photo)
        savePhoto = v.findViewById(R.id.iv_save_photo)
        progressBar = v.findViewById(R.id.progressBar)
        save = v.findViewById(R.id.btn_save)


        
        var  circle : Sprite =Circle()
        progressBar.setIndeterminateDrawable(circle);
        save.setOnClickListener(this)
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
           R.id.btn_save->{
               if(user != null){
                   checkIsEmptyForm()
               }

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
                    savePhoto.visibility = View.VISIBLE
                }
                2->{
                    var uri: Uri =data.data
                    var inputStream: InputStream = context?.contentResolver!!.openInputStream(uri)
                    var bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    avatar.setImageBitmap(bitmap)
                    getRealPathFromURI(uri)
                    savePhoto.visibility = View.VISIBLE
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
            email.setText(user.email)
            address.setText(user.address)
            phonNumber.setText(user.phone_number)
            gender.setText(user.gender)
            if(user.image != ""){
                imageUrl = user.image
                Picasso.get().load(user.image).error(R.drawable.ic_account_circle_black_24dp).into(avatar)
            }
        }
    }
    fun checkIsEmptyForm(){
        if(username.text.isEmpty() ||
                email.text.isEmpty() ||
                gender.text.isEmpty() ||
                phonNumber.text.isEmpty() ||
                address.text.isEmpty()){
            isEmptyForm.setText(getText(R.string.empty))
            isEmptyForm.visibility = View.VISIBLE
        }
        else{
            getData()
            updateProfile()
        }

    }
    fun setData(){
        user.username = username.text.toString()
        user.email = email.text.toString()
        user.gender = gender.text.toString()
        user.phone_number = phonNumber.text.toString()
        user.address = address.text.toString()
        if(imageUrl !=null){
            user.image = imageUrl
        }
    }
    fun upLoadImage(){
        updateOnProgess()
        avatar.isDrawingCacheEnabled = true
        avatar.buildDrawingCache()
        val bitmap = (avatar.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val calendar : Calendar = Calendar.getInstance()
        val mountainsRef = storageRef.child("Image"+user.id+ ".png")
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            updaterOnFail()
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
                    imageUrl = downloadUri.toString()
                    savePhoto.visibility = View.GONE
                    updateOnSuccess()
                } else {

                }
            }
        }
    }
    fun onTextChange(item :EditText){
        item.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.count() ==0){
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {

                }
            }
        })
    }
    fun updateProfile(){
        preHandlerUpdateProfile = PreHandlerUpdateProfile(user, this.context!!,this)
        preHandlerUpdateProfile.updateProfile()
    }
    override fun updateOnProgess() {
        progressBar.visibility = View.VISIBLE
    }

    override fun updateOnSuccess() {
        progressBar.visibility = View.GONE
        isEmptyForm.visibility = View.GONE
        onResume()
    }

    override fun updaterOnFail() {
        progressBar.visibility = View.GONE
        isEmptyForm.visibility = View.GONE
        Toast.makeText(context,"Up load fail",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
    }
}