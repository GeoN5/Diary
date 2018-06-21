package com.example.geonho.retorfitkotlin

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


fun ImageView.loadImage(url : String,context: Context){
    Glide.with(context).load(url).apply(RequestOptions().centerCrop()).into(this)
}

fun ImageView.loadImage(url : Uri, context: Context){
    Glide.with(context).load(url).apply(RequestOptions().centerCrop()).into(this)
}

object RetrofitUtil {

    var retrofit = Retrofit.Builder()
            .baseUrl("http://purplebeen.kr:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val MULTIPART_FORM_DATA = "multipart/form-data"

    fun getLoginRetrofit(context: Context): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val token = SharedPreferenceUtil.getData(context,"token")
            val request = original.newBuilder()
                    .header("authorization", token)
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }

        val client = httpClient.build()
        return Retrofit.Builder()
                .baseUrl("http://purplebeen.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    fun createRequestBody(file: File, name: String): MultipartBody.Part {
        val mFile = RequestBody.create(MediaType.parse("images/*"), file)
        return MultipartBody.Part.createFormData(name, file.name, mFile)
    }

    fun createRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value)
    }
}

object SharedPreferenceUtil {

    fun getData(context : Context, key : String) : String {
        var sharedPreferences : SharedPreferences = context.getSharedPreferences("test",Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,"")
    }

    fun saveData(context: Context, key : String, value : String) {
        var sharedPreferences : SharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }
}