package com.example.zbesp.screens.vehicles//package com.example.zbesp.screens
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.zbesp.screens.bottombar.BottomBarScreen
//
//@Composable
//fun VehicleNavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomBarScreen.Map.route
//    ) {
//        composable(route = "DetailCurrent") {
//            VehiclesScreen()
//        }
//
//        composable(route = BottomBarScreen.Map.route) {
//            MapScreen()
//        }
//
//        composable(route = BottomBarScreen.Settings.route) {
//            SettingsScreen()
//        }
//    }
//}