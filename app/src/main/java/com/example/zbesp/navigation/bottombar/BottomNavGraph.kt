package com.example.zbesp.navigation.bottombar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zbesp.navigation.settings.SettingsNavGraph
import com.example.zbesp.screens.map.MapScreen
import com.example.zbesp.navigation.vehicles.VehiclesNavGraph
import com.example.zbesp.navigation.zones.ZonesNavGraph

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BottomNavGraph(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(route = BottomBarScreen.Vehicles.route) {
            VehiclesNavGraph()
        }

        composable(route = BottomBarScreen.Map.route) {
            MapScreen(context)
        }
        composable(route = BottomBarScreen.Zones.route) {
            ZonesNavGraph(context = context)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsNavGraph(context = context)
        }
    }
}