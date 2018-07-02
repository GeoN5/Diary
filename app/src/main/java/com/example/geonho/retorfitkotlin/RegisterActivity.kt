package com.example.geonho.retorfitkotlin

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import java.io.File

class RegisterActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    var uri : Uri? = null
    lateinit var file : File
    private val REQUEST_GALLERY_CODE =200
    private val READ_REQUEST_CODE = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()
    }

    fun setListeners(){
        profileImage.setOnClickListener {
            image()
        }
        registerButton.setOnClickListener {
            register()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Snackbar.make(window.decorView.rootView,"권한이 없습니다",Snackbar.LENGTH_SHORT).show()
        //현재 액티비티의 최상위 뷰를 가져와서 스낵바를 띄움.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(uri!=null)
            image()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode ==Activity.RESULT_OK){
            uri = data!!.data//사진 data를 가져옴.
            if(EasyPermissions.hasPermissions(applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                profileImage.loadImage(uri!!,applicationContext)//glide
                var filePath : String = getRealPathFromURI(uri!!,this) //실제 path가 담김.
                file = File(filePath)
            }else{
                EasyPermissions.requestPermissions(this@RegisterActivity,"파일을 읽기 위해서는 권한이 필요합니다!",READ_REQUEST_CODE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }
    }

    fun image(){
        val openGalleryIntent = Intent(Intent.ACTION_PICK)
        openGalleryIntent.type="image/*"
        startActivityForResult(openGalleryIntent,REQUEST_GALLERY_CODE)
    }

    private fun getRealPathFromURI(contentURI: Uri,activity: Activity):String{
        val cursor = activity.contentResolver.query(contentURI,null,null,null,null)
        //contentResolver라는 db에서 해당 URI를 탐색할수있는 cursor객체를 받아옴.
        if(cursor ==null){
            return contentURI.path
        }else{
            cursor.moveToFirst() //커서의 위치를 맨 앞인 첫 번째로 옮겨서
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)//data 컬럼의 인덱스를 가져옴.
            return cursor.getString(idx) //해당하는 인덱스의 실제 path를 String으로 가져옴.
        }
    }

    fun register() {
        if (password1EditText.text.toString().equals(password2EditText.text.toString())) {
            var user = User(usernameEditText.text.toString(), password1EditText.text.toString())
            var userService: UserService = RetrofitUtil.retrofit.create(UserService::class.java)
            var signCall: Call<Response> = userService.register(user, RetrofitUtil.createRequestBody(file, "profile"))

            signCall.enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                    if (response?.body()!=null && response!!.body()!!.result.success) {
                        Toast.makeText(applicationContext, response!!.body()!!.result.message, Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, response!!.body()!!.result.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        } else {
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    .setTitle("경고").setMessage("입력하신 비밀번호를 확인해주세요!").setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
            var dialog = builder.create()
            dialog.show()
        }
    }
}
