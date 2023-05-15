package com.example.zbesp.navigation.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.screens.settings.AboutUsScreen
import com.example.zbesp.screens.settings.SettingsScreen
import com.example.zbesp.screens.settings.SubscriptionScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = SettingsScreens.SettingScreen.route,
    context: Context,
    authenticationNavController: NavController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(SettingsScreens.SettingScreen.route) {
            SettingsScreen(navController, context, authenticationNavController)
        }
        composable(SettingsScreens.AboutUsScreen.route) {
            AboutUsScreen(navController)
        }
        composable(SettingsScreens.SubscriptionScreen.route) {
            SubscriptionScreen(navController)
        }
    }
}