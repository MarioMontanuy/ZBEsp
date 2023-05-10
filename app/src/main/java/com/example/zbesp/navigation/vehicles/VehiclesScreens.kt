package com.example.zbesp.navigation.vehicles

sealed class VehiclesScreens(val route: String) {
    object VehiclesList : VehiclesScreens(route = "vehicles_list")
    object VehicleDetail : VehiclesScreens(route = "vehicle_detail")
    object VehicleDetailCommunity : VehiclesScreens(route = "vehicle_detail_community")
    object NewVehicle : VehiclesScreens(route = "new_vehicle")
    object CommunityVehicles : VehiclesScreens(route = "community_vehicles")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}