package com.example.zbesp.screens.bottombar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zbesp.screens.MapScreen
import com.example.zbesp.screens.SettingsScreen
import com.example.zbesp.screens.vehicles.VehiclesNavGraph

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
            SettingsScreen()
        }
    }
}