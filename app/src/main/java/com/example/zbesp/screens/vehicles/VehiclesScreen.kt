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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.VehiclesRepo
import java.util.*
import com.example.zbesp.ui.theme.SapphireBlue
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VehiclesScreen(navController: NavController) {
    val vehicles = remember { VehiclesRepo.getVehicles() }
    LazyColumn {
        item {
            Header("My Vehicles")
        }
        items(vehicles) { vehicle ->
            PostItem(vehicle = vehicle, navController = navController)
            Divider(startIndent = 50.dp)
        }
    }
    VehiclesFloatingActionButton(navController = navController)
}

@Composable
fun VehiclesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = VehiclesScreens.VehiclesList.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(VehiclesScreens.VehiclesList.route) {
            VehiclesScreen(navController = navController)
        }
        composable(VehiclesScreens.VehicleDetail.route) {
            VehicleDetailScreen()
        }
        composable(VehiclesScreens.NewVehicle.route) {
            NewVehicleDetailScreen()
        }
    }
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
            } },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Crear nota")
        }
    }
}


@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SapphireBlue,
        contentColor = Color.White,
        modifier = modifier.semantics { heading() }
    ) {
        androidx.compose.material.Text(
            text = text,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun PostMetadata(
    vehicle: Vehicle,
    modifier: Modifier = Modifier
) {
    val divider = "\n"
    val tagDivider = "\t\t\t"
    val text = buildAnnotatedString {
//        append(vehicle.metadata.type.toString())
//        append(tagDivider)
//        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
//            background = vehicle.metadata.stickerColor.copy(alpha = 0.2f)
//        )
//        withStyle(tagStyle) {
//                append(" Sticker: ${vehicle.metadata.environmentalSticker.toString().uppercase(Locale.getDefault())} ")
//            }
        if (vehicle.metadata.enabled) {
            append("Vehículo activo")
        }
//        post.tags.forEachIndexed { index, tag ->
//            if (index != 0) {
//                append(tagDivider)
//            }
//            withStyle(tagStyle) {
//                append(" ${tag.uppercase(Locale.getDefault())} ")
//            }
//        }
    }
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        androidx.compose.material.Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
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
            .clickable {navController.navigate(VehiclesScreens.VehicleDetail.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }   },
        icon = {
            Image(
                painter = painterResource(vehicle.imageThumbId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .size(45.dp)
            )
        },
        text = {
            Text(text = vehicle.name)
        },
        secondaryText = {
            PostMetadata(vehicle)
        }
    )
}

//@Composable
//@Preview
//fun VehiclesScreenPreview(){
//    VehiclesScreen()
//}