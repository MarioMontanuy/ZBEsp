package com.example.zbesp.screens.settings

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.dataStore
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.getButtonColors
import com.jamal.composeprefs.ui.PrefsScreen
import com.jamal.composeprefs.ui.prefs.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(context: Context) {
    Scaffold(topBar = { ZBEspTopBar("Settings") }) {
        PrefsScreen(dataStore = LocalContext.current.dataStore) {
            prefsGroup("MAP") {
                prefsItem {
                    TextPref(
                        title = "Just some text",
                        summary = "But enabled this time",
                        enabled = true,
                        leadingIcon = { Icon(Icons.Filled.Info, "Info") },
                        onClick = { Log.i("SettingsScreen", "Just some text") }
                    )
                }
                prefsItem {
                    DropDownPref(
                        key = "dd1",
                        title = "Dropdown with currently selected item as summary",
                        useSelectedAsSummary = true,
                        entries = mapOf(
                            "0" to "Entry 1",
                            "1" to "Entry 2",
                            "2" to "Entry 3"
                        )
                    )
                }
                prefsItem {
                    SliderPref(
                        key = "sp3",
                        title = "Select map Zoom",
                        valueRange = 10f..20f,
                        showValue = true
                    )
                }

            }
            prefsGroup("NOTIFICATION") {
                prefsItem {
                    SwitchPref(
                        key = "notification",
                        title = "Enable notifications",
                        summary = "Receive a notification when you enter a LEZ",
                        leadingIcon = { Icon(Icons.Filled.Notifications, "Home") }
                    )
                }
            }
        }
    }
//    Column {
//        Header(text = "Settings")
//        CommonButton(navController, SettingsScreens.AboutUsScreen.route)
//        CommonButton(navController, SettingsScreens.SubscriptionScreen.route)
//    }
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
