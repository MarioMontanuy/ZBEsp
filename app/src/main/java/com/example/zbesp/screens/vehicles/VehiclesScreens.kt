package com.example.zbesp.screens.vehicles

sealed class VehiclesScreens(val route: String) {
    object VehiclesList : VehiclesScreens(route = "vehicles_list")
    object VehicleDetail : VehiclesScreens(route = "vehicle_detail")
    object NewVehicle : VehiclesScreens(route = "new_vehicle")
}