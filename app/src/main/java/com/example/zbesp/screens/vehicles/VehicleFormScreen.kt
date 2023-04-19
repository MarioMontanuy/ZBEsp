package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.errorColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VehicleFormScreen(navController: NavController){
    val value = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.form_screen_title)) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally ) {
            item {
                OutlinedTextField(
                    value = value.value,
                    onValueChange = { value.value = it },
                    label = { Text(stringResource(id = R.string.search_location)) },
                    placeholder = { Text(stringResource(id = R.string.location_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = error.value,
                    trailingIcon = {
                        if (error.value)
                            Icon(Icons.Filled.Error,"error", tint = errorColor)
                    }
                )
            }
        }

    }


}