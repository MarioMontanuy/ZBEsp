package com.example.zbesp.navigation.authentication

sealed class AuthenticationScreens(val route: String) {
    object LogInScreen : AuthenticationScreens(route = "Log In")
    object RegisterScreen : AuthenticationScreens(route = "Register")
    object MainScreen : AuthenticationScreens(route = "Main")
}