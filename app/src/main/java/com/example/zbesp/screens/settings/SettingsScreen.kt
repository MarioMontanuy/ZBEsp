package com.example.zbesp.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.navigation.settings.SettingsScreens
import com.example.zbesp.screens.vehicles.Header
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.getButtonColors

@Composable
fun SettingsScreen(navController: NavController) {
    Column {
        Header(text = "Settings")
        CommonButton(navController, SettingsScreens.AboutUsScreen.route)
        CommonButton(navController, SettingsScreens.SubscriptionScreen.route)
    }
}

@Composable
fun CommonButton(navController: NavController, route: String){
    Button(
        onClick = {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = getButtonColors(),
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(route, TextAlign.Start)
    }
}
