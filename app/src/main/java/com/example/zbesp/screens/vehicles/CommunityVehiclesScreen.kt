package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.domain.Vehicle
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.userEmail
import com.example.zbesp.screens.zones.connectivityEnabled
import com.example.zbesp.ui.theme.OwnerTitle
import com.example.zbesp.ui.theme.SubtitleText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

var communityVehicles = mutableStateOf(listOf<Vehicle>())

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommunityVehiclesScreen(navController: NavController) {
    Scaffold(topBar = {
        ZBEspTopBar(
            stringResource(id = R.string.community_vehicles_screen_title),
            navController
        )
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (communityVehicles.value.isEmpty() || !connectivityEnabled()) {
                item {
                    Spacer(modifier = Modifier.padding(30.dp))
                    SubtitleText(
                        text = stringResource(id = R.string.community_no_vehicles) + "\nor\n" +
                                stringResource(id = R.string.network_error),
                        alignment = TextAlign.Center,
                        MaterialTheme.typography.body1
                    )
                }
            } else {
                communityVehicles.value.groupBy { it.owner }.map {
                    var title = true
                    items(it.value) { vehicle ->
                        if (title) {
                            title = false
                            OwnerTitle(it.value.first().owner)
                        }
                        PostItem(
                            vehicle = vehicle,
                            navController = navController,
                            type = "community"
                        )
                        Divider(startIndent = 50.dp)
                    }
                }

            }
        }
    }
}

fun createCommunityVehiclesListenerOnDatabase() {
    val docRef = Firebase.firestore.collection("vehicles")
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("createCommunityVehiclesListenerOnDatabase", "Listen failed.", e)
            return@addSnapshotListener
        }
        if (snapshot != null && !snapshot.isEmpty) {
            communityVehicles.value = listOf()
            Log.d("createCommunityVehiclesListenerOnDatabase", "Current data:")
            snapshot.forEach { it ->
                val currentVehicle = it.toObject<Vehicle>()
                if (currentVehicle.owner != userEmail) {
                    communityVehicles.value = communityVehicles.value + currentVehicle
                }
            }
        } else {
            Log.d("createCommunityVehiclesListenerOnDatabase", "Current data: null")
            communityVehicles.value = listOf()
        }
    }
}