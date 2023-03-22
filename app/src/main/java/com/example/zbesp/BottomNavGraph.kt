package com.example.zbesp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zbesp.screens.MapScreen
import com.example.zbesp.screens.SettingsScreen
import com.example.zbesp.screens.VehiclesScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(route = BottomBarScreen.Vehicles.route) {
            VehiclesScreen()
        }

        composable(route = BottomBarScreen.Map.route) {
            MapScreen()
        }

        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}