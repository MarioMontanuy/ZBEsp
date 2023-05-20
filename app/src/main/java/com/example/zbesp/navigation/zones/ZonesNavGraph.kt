package com.example.zbesp.navigation.zones

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zbesp.domain.getGeofence
import com.example.zbesp.screens.zones.ZoneCommentsForm
import com.example.zbesp.screens.zones.ZoneCommentsScreen
import com.example.zbesp.screens.zones.ZoneDetailScreen
import com.example.zbesp.screens.zones.ZonesScreen

@Composable
fun ZonesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ZonesScreens.ZoneList.route,
    context: Context
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ZonesScreens.ZoneList.route) {
            ZonesScreen(navController)
        }
        composable(
            route = ZonesScreens.ZoneDetail.route + "/{zone}",
            arguments = listOf(
                navArgument("zone") {
                    type = NavType.StringType
                    defaultValue = "Some Default"
                }
            )
        ) { entry ->
            ZoneDetailScreen(
                zone = getGeofence(entry.arguments?.getString("zone")!!),
                context = context,
                navController = navController
            )
        }
        composable(
            route = ZonesScreens.ZoneComments.route + "/{zone}",
            arguments = listOf(
                navArgument("zone") {
                    type = NavType.StringType
                    defaultValue = "Some Default"
                }
            )
        ) { entry ->
            ZoneCommentsScreen(
                zone = getGeofence(entry.arguments?.getString("zone")!!),
                context = context,
                navController = navController
            )
        }

        composable(
            route = ZonesScreens.ZoneCommentsForm.route + "/{zone}",
            arguments = listOf(
                navArgument("zone") {
                    type = NavType.StringType
                    defaultValue = "Some Default"
                }
            )
        ) { entry ->
            ZoneCommentsForm(
                zone = getGeofence(entry.arguments?.getString("zone")!!),
                context = context,
                navController = navController
            )
        }
    }
}
