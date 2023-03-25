package com.example.zbesp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.example.zbesp.ui.theme.BottomNavigationBarTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.zbesp.ui.theme.SapphireBlue
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavigationBarTheme {
                val systemUiController = rememberSystemUiController()
                val darkTheme = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (darkTheme) Color.LightGray else SapphireBlue
                    )
//                    systemUiController.setNavigationBarColor(
//                        color = if (darkTheme) Color.Black else RoyalBlue
//                    )
                }
                MainScreen()
            }
        }
    }
}

