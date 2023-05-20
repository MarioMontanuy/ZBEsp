package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.domain.GeofenceItem
import com.example.zbesp.domain.Vehicle
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.navigation.zones.ZonesScreens
import com.example.zbesp.screens.VehiclesTopBar
import com.example.zbesp.screens.zones.connectivityEnabled
import java.util.*
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText

var vehicles = mutableStateOf(listOf<Vehicle>())

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VehiclesScreen(navController: NavController) {
    Scaffold(topBar = {
        VehiclesTopBar(
            stringResource(id = R.string.vehicles_screen_title),
            navController
        )
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (vehicles.value.isEmpty() || !connectivityEnabled()) {
                Log.i("vehicles.value", "Empty")
                item {
                    Spacer(modifier = Modifier.padding(30.dp))
                    SubtitleText(
                        text = stringResource(id = R.string.create_vehicle_needed) + "\nor\n" +
                                stringResource(id = R.string.network_error),
                        alignment = TextAlign.Center,
                        MaterialTheme.typography.body1
                    )
                }
            } else {
                items(vehicles.value) { vehicle ->
                    PostItem(vehicle = vehicle, navController = navController, type = "vehicle")
                    Divider(startIndent = 50.dp)
                }
            }
        }
        VehiclesFloatingActionButton(navController = navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    navController: NavController,
    type: String
) {
    ListItem(
        modifier = modifier
            .clickable {
                if (type == "vehicle") {
                    navController.navigate(VehiclesScreens.VehicleDetail.withArgs(vehicle.id.toString()))
                }
                if (type == "community") {
                    navController.navigate(VehiclesScreens.VehicleDetailCommunity.withArgs(vehicle.id.toString()))
                }
            },
        icon = {
            Image(
                painter = if (isSystemInDarkTheme()) painterResource(vehicle.typeImageWhite) else painterResource(
                    vehicle.typeImage
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .size(45.dp)
            )
        },
        text = {
            TitleText(text = vehicle.name, alignment = TextAlign.Justify)
        },
        secondaryText = {
            if (vehicle.enabled) {
                SubtitleText(stringResource(id = R.string.current_vehicle), TextAlign.Justify)
            }
        }
    )
}

@Composable
fun VehiclesFloatingActionButton(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = {
                navController.navigate(VehiclesScreens.NewVehicle.route)
            },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = stringResource(
                    id = R.string.create_note
                )
            )
        }
    }
}

@Composable
fun CommentsFloatingActionButton(zone: GeofenceItem, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = {
                navController.navigate(ZonesScreens.ZoneCommentsForm.withArgs(zone.id.toString()))
            },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = stringResource(
                    id = R.string.create_coment
                )
            )
        }
    }
}