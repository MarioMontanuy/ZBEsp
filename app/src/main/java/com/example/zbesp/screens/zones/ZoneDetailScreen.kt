package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import androidx.navigation.NavGraph.Companion.findStartDestination
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
import com.example.zbesp.data.EnvironmentalSticker
import com.example.zbesp.data.EnvironmentalStickerEnum
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.retrofit.ImageAPI
import com.example.zbesp.retrofit.ImageAPIService
import com.example.zbesp.retrofit.MarsPhoto
import com.example.zbesp.retrofit.ZoneViewModel
import com.example.zbesp.screens.vehicles.PostItem
import com.example.zbesp.ui.theme.SapphireBlueTransparent
import com.example.zbesp.ui.theme.TitleText
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
    val viewModel = ZoneViewModel(context = context)
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
                if (connectivityEnabled()) {
                    Log.i("ConnectivityEnabled", "True")
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(zone.url)
                            .crossfade(true)
                            .build(),
                        loading = {
                            CircularProgressIndicator()
                        },
                        contentDescription = zone.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )
                } else {
                    Log.i("ConnectivityEnabled", "False")
                    Image(
                        painter = painterResource(id = R.drawable.noimage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )
                }

//                Image(
//                    painter = rememberAsyncImagePainter("https://pre.madrid360.es/wp-content/uploads/2021/12/Portada-Madrid-ZBE-2.png"),
//                    contentDescription = null,
//                    modifier = Modifier.size(128.dp)
//                )
                Spacer(modifier = Modifier.padding(vertical = 30.dp))
            }
            item {
                    if (currentVehicle != null) {
                        if (zone.isStickerForbidden(currentVehicle.environmentalSticker)) {
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
                    Spacer(modifier = Modifier.padding(20.dp))
                    SubtitleText(text = "Restrictions for Stickers:", alignment = TextAlign.Justify,
                        style = MaterialTheme.typography.body1)
                    LazyRow(horizontalArrangement = Arrangement.Center) {
                        items(zone.forbiddenStickers) { sticker ->
                            PostSticker(sticker = sticker)
                            Divider(startIndent = 20.dp)
                        }
                    }
                }
            }
                // Shows available stickers of the zone
        }
    ImageRequest.Builder(context = context)
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PostSticker(sticker: EnvironmentalSticker) {
    ListItem(
        modifier = Modifier,
        icon = {
            Image(
                painter = painterResource(id = sticker.stickerImage),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .size(45.dp)
            )
        },
        text = {
            TitleText(text = "", alignment = TextAlign.Justify)
        },

    )
}

