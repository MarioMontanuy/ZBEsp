package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.data.GeofenceItem
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.VehiclesRepo
import com.example.zbesp.data.geofences
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.navigation.zones.ZonesScreens
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.vehicles.PostItem
import com.example.zbesp.screens.vehicles.VehiclesFloatingActionButton
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZonesScreen(navController: NavController) {
    Scaffold(topBar = { ZBEspTopBar("Zones") }) {
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (geofences.isEmpty()) {
                item {
                    Text(modifier = Modifier
                        .fillMaxSize().padding(50.dp),
                        text =  "There is no zone available now",
                        textAlign = TextAlign.Center)
                }
            } else {
                items(geofences) { zone ->
                    PostGeofenceItem(zone, navController = navController)
                    Divider(startIndent = 50.dp)
                }
            }

        }
        VehiclesFloatingActionButton(navController = navController)
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
            .clickable {navController.navigate(ZonesScreens.ZoneDetail.withArgs(geofenceItem.id.toString())) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }   },
        icon = {
            Image(
                painter = painterResource(geofenceItem.imageId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .size(45.dp)
            )
        },
        text = {
            TitleText(text = geofenceItem.name, alignment = TextAlign.Start)
        },
        secondaryText = {
            SubtitleText(geofenceItem.description, TextAlign.Start)

        }
    )
}