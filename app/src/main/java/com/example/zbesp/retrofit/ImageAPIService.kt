package com.example.zbesp.retrofit

import coil.request.ImageRequest
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.io.InputStream
data class MarsPhoto(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
)

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
//    "https://pre.madrid360.es/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

///wp-content/uploads/2021/12/Portada-Madrid-ZBE-2.png
interface ImageAPIService {
    @GET("photos")
    suspend fun getImageDetails(): List<MarsPhoto>
}

object ImageAPI {
    val retrofitService: ImageAPIService by lazy { retrofit.create(ImageAPIService::class.java) }
}
