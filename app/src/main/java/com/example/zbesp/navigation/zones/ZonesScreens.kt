package com.example.zbesp.navigation.zones

sealed class ZonesScreens(val route: String) {
    object ZoneList : ZonesScreens(route = "zone_list")
    object ZoneDetail : ZonesScreens(route = "zone_detail")
    object ZoneComments : ZonesScreens(route = "zone_comments")
    object ZoneCommentsForm : ZonesScreens(route = "zone_comments_form")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}