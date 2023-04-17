package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zbesp.R
import com.example.zbesp.data.GeofenceItem
import com.example.zbesp.data.getCurrentVehicle
import com.example.zbesp.screens.ZBEspTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneDetailScreen(zone: GeofenceItem){
    val currentVehicle = getCurrentVehicle()
    Scaffold(topBar = { ZBEspTopBar("Zone Information") }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = zone.name, style = MaterialTheme.typography.h3)
                Image(
                    painter = painterResource(zone.imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small)
                        .size(200.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.padding(50.dp))
                if (currentVehicle != null) {
                    if (zone.isStickerForbidden(currentVehicle.environmentalSticker.type)) {
                        Text(text = "Your vehicle does not meet the restrictions of this Low Emission Zone")
                    } else {
                        Text(text = "Your vehicle is allowed to travel in this Low Emission Zone")
                    }
                } else {
                    Text(text = "You have to create a vehicle in order to know your restrictions")
                }

            }
        }
    }

}