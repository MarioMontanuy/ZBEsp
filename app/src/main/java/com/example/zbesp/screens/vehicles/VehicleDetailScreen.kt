package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zbesp.R
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.noEnabledVehicleInDatabase
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.*
import java.text.DateFormat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VehicleDetailScreen(vehicle: Vehicle) {
    val vehicleEnabled = remember { mutableStateOf(vehicle.enabled) }
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.vehicle_detail_screen_title)) }) {
        LazyColumn {
            item {
                if (vehicleEnabled.value) {
                    AddEnabledInfoRow()
                    Divider(startIndent = 20.dp)
                }
            }
            item {
                AddTextRow(title = stringResource(id = R.string.name), subtitle = vehicle.name)
            }
            item {
                AddTextRow(
                    title = stringResource(id = R.string.country),
                    subtitle = vehicle.country
                )
            }
            item {
                AddTextRow(
                    title = stringResource(id = R.string.registration_year),
                    subtitle = DateFormat.getDateInstance().format(vehicle.registrationYear)
                )
            }
            item {
                AddTextRow(
                    title = stringResource(id = R.string.type),
                    subtitle = vehicle.type
                )
            }
            item {
                AddTextRow(
                    title = stringResource(id = R.string.environmental_sticker),
                    subtitle = vehicle.environmentalSticker
                )
                Spacer(modifier = Modifier.padding(50.dp))
            }
            item {
                if (!vehicleEnabled.value) {
                    Button(
                        onClick = {
                            noEnabledVehicleInDatabase(vehicle)
                            vehicleEnabled.value = !vehicleEnabled.value
                            vehicle.enabled = !vehicle.enabled
                        },
                        colors = getButtonColorsReversed(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    ) {
                        TopBarTittle(
                            stringResource(id = R.string.mark_current_vehicle),
                            TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTextRow(title: String, subtitle: String) {
    ListItem(
        Modifier.padding(5.dp),
        text = {
            TitleText(text = title, TextAlign.Justify)
        },
        secondaryText = {
            SubtitleText(text = subtitle, TextAlign.Justify)
        },
    )
    Divider(startIndent = 20.dp)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEnabledInfoRow() {
    ListItem(
        Modifier.padding(20.dp),
        text = {
            TitleText(text = stringResource(id = R.string.vehicle_enabled), TextAlign.Center)
        },
        secondaryText = {
            SubtitleText(stringResource(id = R.string.information_shown), TextAlign.Center)
        },
    )
    Divider(startIndent = 20.dp)
}