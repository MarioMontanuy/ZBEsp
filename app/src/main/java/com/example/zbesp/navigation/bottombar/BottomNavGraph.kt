package com.example.zbesp.navigation.bottombar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zbesp.navigation.settings.SettingsNavGraph
import com.example.zbesp.screens.map.MapScreen
import com.example.zbesp.navigation.vehicles.VehiclesNavGraph

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(route = BottomBarScreen.Vehicles.route) {
            VehiclesNavGraph()
        }

        composable(route = BottomBarScreen.Map.route) {
            MapScreen()
        }

        composable(route = BottomBarScreen.Settings.route) {
            SettingsNavGraph()
        }
    }
}