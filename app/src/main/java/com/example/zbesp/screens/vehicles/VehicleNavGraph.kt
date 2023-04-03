package com.example.zbesp.screens.vehicles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun VehiclesNavGraph(
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