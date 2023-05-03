package com.example.zbesp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.navigation.bottombar.BottomBarScreen
import com.example.zbesp.navigation.bottombar.BottomNavGraph
import com.example.zbesp.ui.theme.TopBarTittle
import com.example.zbesp.R
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.createIdOnDatabase
import com.example.zbesp.data.vehicles
import com.example.zbesp.data.vehiclesDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context) {
    createIdOnDatabase()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(modifier = Modifier.padding(padding)) {
                BottomNavGraph(navController = navController, context)
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Vehicles,
        BottomBarScreen.Map,
        BottomBarScreen.Zones,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title, color = Color.White)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(id = R.string.navigation_icon),
                tint = Color.White
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            if (screen.route == "vehicles") {
                getVehiclesFromDatabase()
            }
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

fun getVehiclesFromDatabase() {
    vehiclesDatabase.get().addOnSuccessListener {
        it.forEach {
                value ->
            // Poner el value.toObject<Vehicle>()
            val id = value.data["id"] as Long
            val name = value.data["name"] as String
            val country = value.data["country"] as String
            val type = value.data["type"] as String
            val registrationYear = value.data["registrationYear"] as com.google.firebase.Timestamp
            val environmentalSticker = value.data["environmentalSticker"] as String
            val enabled = value.data["enabled"] as Boolean
            val stickerImage = value.data["stickerImage"] as Long
            val typeImage = value.data["typeImage"] as Long
            val typeImageWhite = value.data["typeImageWhite"] as Long
            val currentVehicle = Vehicle(
                id, name, country, type, registrationYear.toDate(), environmentalSticker,
                enabled, stickerImage.toInt(), typeImage.toInt(), typeImageWhite.toInt())
            addVehicleIfNotIn(currentVehicle)
        }
    }
}

fun addVehicleIfNotIn(currentVehicle: Vehicle) {
    vehicles.forEach {
        if (it.id == currentVehicle.id){
            return
        }
    }
    vehicles = vehicles + currentVehicle

}
@Composable
fun ZBEspTopBar(title: String) {
    TopAppBar(
        title = {
            TopBarTittle(text = title, alignment = TextAlign.Justify)
        }
    )
}
