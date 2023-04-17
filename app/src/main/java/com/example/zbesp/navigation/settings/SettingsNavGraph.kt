package com.example.zbesp.navigation.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.screens.settings.AboutUsScreen
import com.example.zbesp.screens.settings.SettingsScreen
import com.example.zbesp.screens.settings.SubscriptionScreen

@Composable
fun SettingsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = SettingsScreens.SettingScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(SettingsScreens.SettingScreen.route) {
            SettingsScreen(navController)
        }
        composable(SettingsScreens.AboutUsScreen.route) {
            AboutUsScreen()
        }
        composable(SettingsScreens.SubscriptionScreen.route) {
            SubscriptionScreen()
        }
    }
}