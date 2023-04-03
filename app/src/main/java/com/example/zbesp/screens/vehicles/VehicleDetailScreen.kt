package com.example.zbesp.screens.vehicles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zbesp.data.Vehicle


@Composable
fun VehicleDetailScreen(name: Vehicle?){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = remember {
            mutableStateOf(TextFieldValue())
        }

        Text(
            text = name!!.name
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = name.metadata.country
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = name.metadata.registrationYear
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = name.metadata.type.toString()
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = name.metadata.environmentalSticker.toString()
        )
        Spacer(modifier = Modifier.height(15.dp))

    }
}