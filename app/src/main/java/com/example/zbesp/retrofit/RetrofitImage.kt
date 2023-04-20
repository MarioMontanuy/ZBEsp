package com.example.zbesp.retrofit

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


fun getImage(name: String): Bitmap? {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.ocu.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: RefrofitImageAPI = retrofit.create(RefrofitImageAPI::class.java)
    val call: Call<ResponseBody> = service.getImageDetails()
    var image : Bitmap? = null
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            try {
                Log.d("onResponse", "Response came from server")
                image = downloadImage(response.body()!!)
            } catch (e: Exception) {
                Log.d("onResponse", "There is an error")
                e.printStackTrace()
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.d("onFailure", t.toString());
        }
    })
    return image
}

fun downloadImage(body: ResponseBody): Bitmap? {
    try {
        Log.d("DownloadImage", "Reading and writing file")
        var `in`: InputStream? = null
        try {
            `in` = body.byteStream()
            return BitmapFactory.decodeStream(`in`)
        } catch (e: IOException) {
            Log.d("DownloadImage", e.toString())
        } finally {
            `in`?.close()
        }

    } catch (e: IOException) {
        Log.d("DownloadImage", e.toString())
    }
    return null
}