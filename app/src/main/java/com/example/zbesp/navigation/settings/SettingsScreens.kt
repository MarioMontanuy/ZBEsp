package com.example.zbesp.navigation.settings

sealed class SettingsScreens(val route: String) {
    object SettingScreen : SettingsScreens(route = "Settings")
    object AboutUsScreen : SettingsScreens(route = "About Us")
    object SubscriptionScreen : SettingsScreens(route = "Pricing")
}