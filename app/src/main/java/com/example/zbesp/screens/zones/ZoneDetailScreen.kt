package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.zbesp.domain.GeofenceItem
import com.example.zbesp.ui.theme.BigTitleText
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.R
import com.example.zbesp.domain.EnvironmentalSticker
import com.example.zbesp.domain.getCurrentVehicle
import com.example.zbesp.screens.ZonesDetailTopBar
import com.example.zbesp.ui.theme.TitleText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneDetailScreen(zone: GeofenceItem, context: Context, navController: NavController) {
    val currentVehicle = getCurrentVehicle()
    Scaffold(topBar = {
        ZonesDetailTopBar(
            stringResource(id = R.string.zone_detail_screen_title),
            navController,
            zone
        )
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            item {
                BigTitleText(text = zone.name, alignment = TextAlign.Justify)
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
                Spacer(modifier = Modifier.padding(vertical = 30.dp))
            }
            item {
                if (currentVehicle != null) {
                    if (zone.isStickerForbidden(currentVehicle.environmentalSticker)) {
                        SubtitleText(
                            text = stringResource(id = R.string.vehicle_named) + " "
                                    + currentVehicle.name + " " + stringResource(
                                id = R.string.not_meet_restrictions
                            ), alignment = TextAlign.Justify,
                            style = MaterialTheme.typography.body1
                        )
                    } else {
                        SubtitleText(
                            text = stringResource(id = R.string.vehicle_named)
                                    + " " + currentVehicle.name + " " + stringResource(
                                id = R.string.meet_restrictions
                            ), alignment = TextAlign.Justify,
                            style = MaterialTheme.typography.body1
                        )
                    }
                } else {
                    SubtitleText(
                        text = stringResource(id = R.string.create_vehicle_restrictions),
                        alignment = TextAlign.Justify,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                SubtitleText(
                    text = "Restrictions for Stickers:", alignment = TextAlign.Justify,
                    style = MaterialTheme.typography.body1
                )
                LazyRow(horizontalArrangement = Arrangement.Center) {
                    items(zone.forbiddenStickers) { sticker ->
                        PostSticker(sticker = sticker)
                        Divider(startIndent = 20.dp)
                    }
                }
            }
        }
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

