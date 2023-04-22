package com.example.zbesp.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.io.InputStream

private const val BASE_URL =
    "https://pre.madrid360.es/wp-content/uploads/2021/12/Portada-Madrid-ZBE-2.png"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ImageAPIService {
    @GET("/wp-content/uploads/2021/12/Portada-Madrid-ZBE-2.png")
    suspend fun getImageDetails(): InputStream
}

object ImageAPI {
    val retrofitService: ImageAPIService by lazy { retrofit.create(ImageAPIService::class.java) }
}
