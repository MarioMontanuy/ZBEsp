package com.example.zbesp.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.zbesp.screens.vehicles.Header

@Composable
fun AboutUsScreen(){
    Column() {
        Header(text = "About Us")
        Text(text = "This application gives information about Spanish ZBE")
    }
}