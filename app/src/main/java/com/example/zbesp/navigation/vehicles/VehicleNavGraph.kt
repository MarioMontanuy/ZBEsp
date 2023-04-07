package com.example.zbesp.navigation.vehicles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zbesp.data.getVehicle
import com.example.zbesp.screens.vehicles.NewVehicleDetailScreen
import com.example.zbesp.screens.vehicles.VehicleDetailScreen
import com.example.zbesp.screens.vehicles.VehiclesScreen

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
        composable(
            route = VehiclesScreens.VehicleDetail.route + "/{vehicle}",
            arguments = listOf(
                navArgument("vehicle") {
                    type = NavType.StringType
                    defaultValue = "Some Default"
                }
            )
        ) { entry ->
            VehicleDetailScreen(vehicle = getVehicle(entry.arguments?.getString("vehicle")!!))
        }
        composable(VehiclesScreens.NewVehicle.route) {
            NewVehicleDetailScreen()
        }
    }
}