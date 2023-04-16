package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.VehiclesRepo
import com.example.zbesp.data.vehicleNone
import com.example.zbesp.data.vehicles
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.screens.ZBEspTopBar
import java.util.*
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VehiclesScreen(navController: NavController) {
    val vehicles = remember { VehiclesRepo.getVehicles() }
    Scaffold(topBar = { ZBEspTopBar("Vehicles") }) {
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (vehicles.isEmpty()) {
                item {
                    Text(modifier = Modifier
                        .fillMaxSize().padding(50.dp),
                        text =  "You have not created a vehicle yet",
                        textAlign = TextAlign.Center)
                }
            } else {
                items(vehicles) { vehicle ->
                    PostItem(vehicle = vehicle, navController = navController)
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
    navController: NavController
) {
    ListItem(
        modifier = modifier
            .clickable {navController.navigate(VehiclesScreens.VehicleDetail.withArgs(vehicle.id.toString())) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }   },
        icon = {
            Image(
                painter = if (isSystemInDarkTheme()) painterResource(vehicle.changeToWhite(vehicle.type)) else painterResource(vehicle.imageId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .size(45.dp)
            )
        },
        text = {
            TitleText(text = vehicle.name, alignment = TextAlign.Start)
        },
        secondaryText = {
            if (vehicle.enabled) {
                SubtitleText("Current Vehicle", TextAlign.Start)
            }
        }
    )
}
@Composable
fun VehiclesFloatingActionButton(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { navController.navigate(VehiclesScreens.NewVehicle.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
                },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Crear nota")
        }
    }
}