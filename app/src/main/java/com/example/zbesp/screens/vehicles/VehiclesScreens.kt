package com.example.zbesp.screens.vehicles

import com.example.zbesp.data.Vehicle

sealed class VehiclesScreens(val route: String) {
    object VehiclesList : VehiclesScreens(route = "vehicles_list")
    object VehicleDetail : VehiclesScreens(route = "vehicle_detail")
    object NewVehicle : VehiclesScreens(route = "new_vehicle")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}