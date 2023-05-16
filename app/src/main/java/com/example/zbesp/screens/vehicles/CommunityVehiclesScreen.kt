package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.idDatabase
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.userEmail
import com.example.zbesp.ui.theme.OwnerTitle
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

var communityVehicles = mutableStateOf(listOf<Vehicle>())


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommunityVehiclesScreen(navController: NavController){
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.community_vehicles_screen_title), navController) }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (communityVehicles.value.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.padding(30.dp))
                    SubtitleText(
                        text = stringResource(id = R.string.community_no_vehicles),
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
                        PostItem(vehicle = vehicle, navController = navController, type = "community")
                        Divider(startIndent = 50.dp)
                    }
                }

            }

        }
    }
}
fun getCommunityVehicles() {
    communityVehicles.value = listOf()
    idDatabase.get().addOnSuccessListener { it ->
        it.forEach {
            if (it.id != userEmail) {
//                getFirestore().collection(it.id).get().addOnSuccessListener {
                    Firebase.firestore.collection(it.id).get().addOnSuccessListener {
                    value ->
                    value.forEach { vehicle ->
                        communityVehicles.value = communityVehicles.value + vehicle.toObject<Vehicle>()
                    }
                }
            }
        }
    }
}