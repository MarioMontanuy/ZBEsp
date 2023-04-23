package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.zbesp.data.GeofenceItem
import com.example.zbesp.data.getCurrentVehicle
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.BigTitleText
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.R
import com.example.zbesp.retrofit.ImageAPI
import com.example.zbesp.retrofit.ImageAPIService
import com.example.zbesp.retrofit.MarsPhoto
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


var currentImage: Unit? = null
// TODO add info about what stickers can access each zone and more info
// TODO Fix image
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneDetailScreen(zone: GeofenceItem, context: Context){
    val currentVehicle = getCurrentVehicle()
//    var image = getImage(zone.name)
    val viewModel = OverviewViewModel(context = context)
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.zone_detail_screen_title)) }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            item {
                BigTitleText(text = zone.name, alignment = TextAlign.Justify)
//                Image(
//                bitmap = viewModel.image.value.asImageBitmap(),
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier
//                        .clip(shape = MaterialTheme.shapes.small)
//                        .size(200.dp)
//                )
//                AsyncImage(
//                    model =
//                    placeholder = painterResource(R.drawable.noimage),
//                    contentDescription = "ZBE",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.clip(CircleShape)
//                )
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(zone.url)
                        .crossfade(true)
                        .build(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = "ZBE",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
//                Image(
//                    painter = rememberAsyncImagePainter("https://pre.madrid360.es/wp-content/uploads/2021/12/Portada-Madrid-ZBE-2.png"),
//                    contentDescription = null,
//                    modifier = Modifier.size(128.dp)
//                )
                Spacer(modifier = Modifier.padding(vertical = 30.dp))

                // Shows available stickers of the zone
                if (currentVehicle != null) {
                    if (zone.isStickerForbidden(currentVehicle.environmentalSticker.type)) {
                        SubtitleText(text = stringResource(id = R.string.vehicle_named) + " "
                                + currentVehicle.name + " " +stringResource(
                            id = R.string.not_meet_restrictions
                        ), alignment = TextAlign.Justify,
                            style = MaterialTheme.typography.body1)
                    } else {
                        SubtitleText(text = stringResource(id = R.string.vehicle_named)
                                + " " + currentVehicle.name+ " " + stringResource(
                            id = R.string.meet_restrictions
                        ), alignment = TextAlign.Justify,
                            style = MaterialTheme.typography.body1)
                    }
                } else {
                    SubtitleText(text = stringResource(id = R.string.create_vehicle_restrictions),
                        alignment = TextAlign.Justify,
                        style = MaterialTheme.typography.body1)
                }
            }
        }
    }

    ImageRequest.Builder(context = context)
}

class OverviewViewModel(context: Context) : ViewModel() {

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

