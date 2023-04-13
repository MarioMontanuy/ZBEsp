package com.example.zbesp.screens.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.dataStore
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.TitleTextWhite
import com.example.zbesp.ui.theme.getButtonColors
import com.jamal.composeprefs.ui.PrefsScreen
import com.jamal.composeprefs.ui.prefs.TextPref

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen() {
    Scaffold(topBar = { ZBEspTopBar("Settings") }) {
        PrefsScreen(dataStore = LocalContext.current.dataStore) {
            prefsGroup("Title") {
                prefsItem {
                    TextPref(
                        title = "Just some text",
                        summary = "But enabled this time",
                        enabled = true,
                        leadingIcon = { Icon(Icons.Filled.Info, "Info") },
                        onClick = { Log.i("SettingsScreen", "Just some text") }
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


@Preview
@Composable
fun SettingsScreensPreview() {
    SettingsScreen()
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
