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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.navigation.bottombar.BottomBarScreen
import com.example.zbesp.navigation.bottombar.BottomNavGraph
import com.example.zbesp.ui.theme.TopBarTittle
import com.example.zbesp.R
import com.example.zbesp.domain.Comment
import com.example.zbesp.domain.GeofenceItem
import com.example.zbesp.domain.Vehicle
import com.example.zbesp.domain.createIdOnDatabase
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.navigation.zones.ZonesScreens
import com.example.zbesp.screens.vehicles.createCommunityVehiclesListenerOnDatabase
import com.example.zbesp.screens.vehicles.vehicles
import com.example.zbesp.screens.zones.comments
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context, authenticationNavController: NavController) {
    createIdOnDatabase()
    createVehicleListenerOnDatabase()
    createCommunityVehiclesListenerOnDatabase()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(modifier = Modifier.padding(padding)) {
                BottomNavGraph(navController = navController, context, authenticationNavController)
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
            navController.navigate(screen.route)
        }
    )
}

@Composable
fun ZBEspTopBar(title: String) {
    TopAppBar(
        title = {
            TopBarTittle(text = title, alignment = TextAlign.Justify)
        }
    )
}

@Composable
fun ZBEspTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            TopBarTittle(text = title, alignment = TextAlign.Justify)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to Vehicles"
                )
            }
        }
    )
}

@Composable
fun VehiclesTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            TopBarTittle(text = title, alignment = TextAlign.Justify)
        },
        actions = {
            IconButton(onClick = { navController.navigate(VehiclesScreens.CommunityVehicles.route) }) {
                Icon(
                    imageVector = Icons.Filled.Groups,
                    contentDescription = "Shared Vehicles"
                )
            }
        }
    )
}

@Composable
fun ZonesDetailTopBar(title: String, navController: NavController, zone: GeofenceItem) {
    TopAppBar(
        title = {
            TopBarTittle(text = title, alignment = TextAlign.Justify)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to Vehicles"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(ZonesScreens.ZoneComments.withArgs(zone.id.toString()))
            }) {
                Icon(
                    imageVector = Icons.Filled.Comment,
                    contentDescription = "Shared Vehicles"
                )
            }
        }
    )
}

fun createVehicleListenerOnDatabase() {
    val docRef = Firebase.firestore.collection("vehicles")
//    val docRef = getFirestore().collection(userEmail)
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.d("createListenerOnDatabase", "Listen failed.", e)
            return@addSnapshotListener
        }
        if (snapshot != null && !snapshot.isEmpty) {
            vehicles.value = listOf()
            Log.d("createListenerOnDatabase", "Current data:")
            snapshot.forEach { it ->
                val currentVehicle = it.toObject<Vehicle>()
                if (currentVehicle.owner == userEmail) {
                    vehicles.value = vehicles.value + currentVehicle
                }
            }
        } else {
            Log.d("createListenerOnDatabase", "Current data: null")
            vehicles.value = listOf()
        }
    }
}

fun createZoneListenerOnDatabase() {
    val docRefComments = Firebase.firestore.collection("comments")
    docRefComments.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("zoneListener", "Listen failed.", e)
            return@addSnapshotListener
        }
        if (snapshot != null && !snapshot.isEmpty) {
            comments.value = listOf()
            Log.d("zoneListener", "Current data:")
            snapshot.forEach { it ->
                Log.d("zoneListener", "adding comment")
                val currentComment = it.toObject<Comment>()
                comments.value = comments.value + currentComment
            }
        } else {
            Log.d("zoneListener", "Current data: null")
            comments.value = listOf()
        }
    }
}