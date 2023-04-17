package com.example.zbesp.navigation.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Vehicles : BottomBarScreen(
        route = "vehicles",
        title = "Vehicles",
        icon = Icons.Default.DirectionsCar
    )

    object Map : BottomBarScreen(
        route = "map",
        title = "Map",
        icon = Icons.Default.Map
    )

    object Zones : BottomBarScreen(
        route = "zones",
        title = "Zones",
        icon = Icons.Default.List
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}