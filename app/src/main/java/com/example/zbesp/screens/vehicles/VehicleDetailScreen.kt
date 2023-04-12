package com.example.zbesp.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zbesp.data.Vehicle
import com.example.zbesp.R
import com.example.zbesp.data.noEnabledVehicle
import com.example.zbesp.data.vehicleNone
import com.example.zbesp.data.vehicles
import com.example.zbesp.ui.theme.*


@Composable
fun VehicleDetailScreen(vehicle: MutableState<Vehicle>) {

    Column {
        Header(text = "Vehicle")
        if(vehicle.value.metadata.value.enabled.value)
        {
            AddEnabledInfoRow()
            Divider(startIndent = 20.dp)
        }
        AddTextRow(title = "Name", subtitle = vehicle.value.name)
        AddTextRow(title = "Country", subtitle = vehicle.value.metadata.value.country)
        AddTextRow(title = "Registration Year", subtitle = vehicle.value.metadata.value.registrationYear)
        AddTextRow(title = "Type", subtitle = vehicle.value.metadata.value.type.toString())
        AddTextRow(title = "Environmental Sticker", subtitle = vehicle.value.metadata.value.environmentalSticker.toString())
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = {
                // TODO hacer esto más eficiente
                noEnabledVehicle()
                // TODO al pulsar el botón, debe aparecer automaticamente el texto que indica q el
                //  vehiculo esta habilitado
                vehicle.value.metadata.value.enabled = mutableStateOf(true)
                vehicles.value = vehicles.value + vehicleNone
            },
            colors = getButtonColorsReversed(),
            modifier = Modifier.fillMaxWidth().padding(20.dp),
        ) {
            TitleTextWhite("Mark as current vehicle", TextAlign.Start)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTextRow(title: String, subtitle: String){
    ListItem(
        Modifier.padding(5.dp),
        text = {
            TitleText(text = title, TextAlign.Start)
        },
        secondaryText = {
            SubtitleText(text = subtitle, TextAlign.Start)
        },
    )
    Divider(startIndent = 20.dp)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEnabledInfoRow(){
    ListItem(
        Modifier.padding(20.dp),
        text = {
            TitleText(text = "This vehicle is enabled", TextAlign.Center)
        },
        secondaryText = {
            SubtitleText("All information shown is related to the characteristics of this vehicle", TextAlign.Center)
        },
    )
    Divider(startIndent = 20.dp)
}
//    Surface(
//        modifier = Modifier
//    )
//    { VehicleCard() }
//    Column(
//        modifier = Modifier
//            .padding(20.dp)
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Card {
//            Text(
//                modifier = Modifier.fillMaxWidth().padding(20.dp),
//                text = name!!.name,
//                style = Typography.titleSmall
//            )
//        }
//        Text(
//            text = name!!.name,
//            style = Typography.titleSmall
//        )
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(
//            text = name.metadata.country,
//            style = Typography.titleSmall
//        )
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(
//            text = name.metadata.registrationYear,
//            style = Typography.titleSmall
//        )
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(
//            text = name.metadata.type.toString(),
//            style = Typography.titleSmall
//        )
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(
//            text = name.metadata.environmentalSticker.toString(),
//            style = Typography.titleSmall
//        )
//        Spacer(modifier = Modifier.height(15.dp))
//    }

@Composable
fun VehicleCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = SapphireBlue.copy(0.2f)
    ) {
        Row(modifier = Modifier) {
            ProfilePictureComposable()
            ProfileContentComposable()
        }
        Row(modifier = Modifier) {
            ProfilePictureComposable()
            ProfileContentComposable()
        }
    }
}
@Composable
fun ProfilePictureComposable() {
    Card(
        modifier = Modifier.size(48.dp),
        backgroundColor = SapphireBlue.copy(0.2f)
//        elevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.vehicle),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp),
            contentDescription = "Profile picture holder"
        )
    }
}
@Composable
fun ProfileContentComposable() {
    Column(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
    ) {
        Text("Catalin Ghita", fontWeight = FontWeight.Bold)
        Text(
            text = "Active now",
            style = MaterialTheme.typography.body2
        )
    }
}