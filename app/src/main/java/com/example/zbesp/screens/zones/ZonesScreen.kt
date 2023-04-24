package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Text
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.zbesp.data.GeofenceItem
import com.example.zbesp.data.geofences
import com.example.zbesp.navigation.zones.ZonesScreens
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZonesScreen(navController: NavController) {
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.zone_screen_title)) }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (geofences.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(50.dp),
                        text = stringResource(id = R.string.zones_not_available),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(geofences) { zone ->
                    PostGeofenceItem(zone, navController = navController)
                    Divider(startIndent = 50.dp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostGeofenceItem(
    geofenceItem: GeofenceItem,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ListItem(
        modifier = modifier
            .clickable {
                navController.navigate(ZonesScreens.ZoneDetail.withArgs(geofenceItem.id.toString())) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
        icon = {
//            Image(
//                painter = painterResource(geofenceItem.imageId),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .clip(shape = MaterialTheme.shapes.small)
//                    .size(45.dp)
//            )
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(geofenceItem.url)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = geofenceItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(MaterialTheme.shapes.small).size(45.dp)
            )
        },
        text = {
            TitleText(text = geofenceItem.name, alignment = TextAlign.Justify)
        },
        secondaryText = {
            SubtitleText(geofenceItem.description, TextAlign.Justify)

        }
    )
}