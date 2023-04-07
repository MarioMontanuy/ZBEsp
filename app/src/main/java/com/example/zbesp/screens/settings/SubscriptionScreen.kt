package com.example.zbesp.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.zbesp.screens.vehicles.Header

@Composable
fun SubscriptionScreen(){
    Column() {
        Header(text = "Subscription")
        Text(text = "Register your data and add functionality according to our prices")
    }
}