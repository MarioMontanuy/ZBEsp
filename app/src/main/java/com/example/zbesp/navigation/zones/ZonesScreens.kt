package com.example.zbesp.navigation.zones

sealed class ZonesScreens(val route: String) {
    object ZoneList : ZonesScreens(route = "zone_list")
    object ZoneDetail : ZonesScreens(route = "zone_detail")
    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}