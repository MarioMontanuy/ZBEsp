package com.example.zbesp.screens.settings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.dataStore
import com.example.zbesp.navigation.settings.SettingsScreens
import com.example.zbesp.screens.ZBEspTopBar
import com.jamal.composeprefs.ui.PrefsScreen
import com.jamal.composeprefs.ui.prefs.*
import com.example.zbesp.R
import com.example.zbesp.navigation.authentication.AuthenticationNavGraph
import com.example.zbesp.navigation.authentication.AuthenticationScreens
import com.example.zbesp.screens.goToLogIn
import com.google.firebase.auth.FirebaseAuth
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, context: Context, authenticationNavController: NavController) {

    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.settings_screen_title)) }) {
        PrefsScreen(dataStore = LocalContext.current.dataStore) {
            prefsGroup("MAP") {
                prefsItem {
                    SliderPref(
                        key = stringResource(id = R.string.map_zoom_key),
                        title = stringResource(id = R.string.map_zoom),
                        valueRange = 10f..20f,
                        showValue = true,
                        defaultValue = 15f
                    )
                }

            }
            prefsItem {
                SwitchPref(
                    key = stringResource(id = R.string.notification_key),
                    title = stringResource(id = R.string.notification_enabled),
                    summary = stringResource(id = R.string.notification_enabled_summary),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Notifications,
                            stringResource(id = R.string.home)
                        )
                    },
                    defaultChecked = false
                )
            }
            prefsGroup("EXTRAS") {
                prefsItem {
                    TextPref(
                        title = stringResource(id = R.string.subscription),
                        summary = stringResource(id = R.string.subscription_summary),
                        enabled = true,
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Subscriptions,
                                stringResource(id = R.string.subscription)
                            )
                        },
                        onClick = {
                            navController.navigate(SettingsScreens.SubscriptionScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            prefsGroup("ABOUT US") {
                prefsItem {
                    TextPref(
                        title = stringResource(id = R.string.info),
                        summary = stringResource(id = R.string.info_summary),
                        enabled = true,
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Info,
                                stringResource(id = R.string.info)
                            )
                        },
                        onClick = {
                            navController.navigate(SettingsScreens.AboutUsScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            prefsGroup("USER") {
                prefsItem {
                    TextPref(
                        title = "Log out",
                        enabled = true,
                        leadingIcon = {
                            Icon(
                                Icons.Filled.PowerOff,
                                stringResource(id = R.string.info)
                            )
                        },
                        onClick = {
                            FirebaseAuth.getInstance().signOut()
                            authenticationNavController.navigate(AuthenticationScreens.LogInScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    )
                }

            }
        }
    }
}
