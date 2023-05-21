package com.example.zbesp.navigation.vehicles

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zbesp.domain.getVehicle
import com.example.zbesp.domain.getVehicleCommunity
import com.example.zbesp.screens.vehicles.*

@Composable
fun VehiclesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = VehiclesScreens.VehiclesList.route,
    context: Context
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
            VehicleDetailScreen(
                vehicle = getVehicle(entry.arguments?.getString("vehicle")),
                navController
            )
        }
        composable(
            route = VehiclesScreens.VehicleDetailCommunity.route + "/{vehicle}",
            arguments = listOf(
                navArgument("vehicle") {
                    type = NavType.StringType
                    defaultValue = "Some Default"
                }
            )
        ) { entry ->
            VehicleDetailScreen(
                vehicle = getVehicleCommunity(entry.arguments?.getString("vehicle")),
                navController
            )
        }
        composable(VehiclesScreens.NewVehicle.route) {
            val viewModel = MainViewModel()
            FormScreen(viewModel, navController, context)
        }
        composable(VehiclesScreens.CommunityVehicles.route) {
            CommunityVehiclesScreen(navController = navController)
        }
    }
}