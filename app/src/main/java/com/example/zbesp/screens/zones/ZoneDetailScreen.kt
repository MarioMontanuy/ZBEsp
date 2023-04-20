package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zbesp.data.GeofenceItem
import com.example.zbesp.data.getCurrentVehicle
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.BigTitleText
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.R
import com.example.zbesp.retrofit.getImage
import retrofit2.Retrofit

// TODO add info about what stickers can access each zone and more info
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneDetailScreen(zone: GeofenceItem){
    val currentVehicle = getCurrentVehicle()
    val image = getImage(zone.name)
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
                Image(
                    bitmap = image?.asImageBitmap() ?: ImageBitmap.imageResource(id = R.drawable.noimage),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small)
                        .size(200.dp)
                )
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
}

