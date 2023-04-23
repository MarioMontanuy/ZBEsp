package com.example.zbesp.retrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ZoneViewModel(context: Context) : ViewModel() {

    //    // The internal MutableLiveData that stores the status of the most recent request
//    private val _status = MutableLiveData<String>()
//
//    // The external immutable LiveData for the request status
//    val status: LiveData<String> = _status
    private val _photos = MutableLiveData<MarsPhoto>()
//    var photos: ImageRequest? = null

    //    var image = mutableStateOf<ImageRequest>()
//    (BitmapFactory.decodeResource(context.resources ,R.drawable.noimage))
    init {
        getMarsPhotos()
    }
    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
//                val test = ImageAPI.retrofitService.getImageDetails()
                val listResult = ImageAPI.retrofitService.getImageDetails()
//                _photos.value = listResult.first()
//                Log.i("getMarsPhotos", "successfully: " + _photos.value)
                Log.i("getMarsPhotos", listResult.first().toString())
//                val img = _photos.value!!.imgSrcUrl.toUri().buildUpon().scheme("https").build() ?: null
//                image.value = BitmapFactory.decodeStream()
                Log.i("getMarsPhotos", "successfully")
//                Log.i("getMarsPhotos", img!!.query!!)
            } catch (e: Exception) {
                Log.i("getMarsPhotos", "error")
            }
        }
    }
}