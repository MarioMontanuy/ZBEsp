package com.example.zbesp.navigation.authentication

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.navigation.settings.SettingsScreens
import com.example.zbesp.screens.LogInScreen
import com.example.zbesp.screens.MainScreen
import com.example.zbesp.screens.RegisterScreen
import com.example.zbesp.screens.ResetPassword
import com.example.zbesp.screens.settings.AboutUsScreen
import com.example.zbesp.screens.settings.SettingsScreen
import com.example.zbesp.screens.settings.SubscriptionScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AuthenticationNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = AuthenticationScreens.LogInScreen.route,
    context: Context
){
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuthenticationScreens.LogInScreen.route) {
            LogInScreen(navController, context)
        }
        composable(AuthenticationScreens.RegisterScreen.route) {
            RegisterScreen(navController, context)
        }
        composable(AuthenticationScreens.MainScreen.route) {
            MainScreen(context, navController)
        }
        composable(AuthenticationScreens.ResetPassword.route) {
            ResetPassword(navController, context)
        }
    }
}