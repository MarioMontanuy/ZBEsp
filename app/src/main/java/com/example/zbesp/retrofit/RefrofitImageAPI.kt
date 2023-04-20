package com.example.zbesp.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface RefrofitImageAPI {
    // TODO Fix URL
    @GET("-/media/ocu/images/home/coches/coches/mapa%20zona%20bajas%20emisiones%20barcelona.jpg?rev=f8af675e-a912-4ee2-9b03-137ed935ac36&hash=904A7F9E2663EDCD8E54DC306CE0E793")
    fun getImageDetails(): Call<ResponseBody>
}