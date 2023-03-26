package com.example.zbesp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.VehiclesRepo
import java.util.*
import com.example.zbesp.ui.theme.SapphireBlue
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VehiclesScreen() {
    val vehicles = remember { VehiclesRepo.getVehicles() }
    LazyColumn {
        item {
            Header("My Vehicles")
        }
        items(vehicles) { vehicle ->
            PostItem(vehicle = vehicle)
            Divider(startIndent = 50.dp)
        }
    }
    VehiclesFloatingActionButton()
}
@Composable
fun VehiclesFloatingActionButton() {
    Box(modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { /* Tus acciones */ },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Crear nota")
        }
    }
}


@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SapphireBlue,
        contentColor = Color.White,
        modifier = modifier.semantics { heading() }
    ) {
        androidx.compose.material.Text(
            text = text,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun PostMetadata(
    vehicle: Vehicle,
    modifier: Modifier = Modifier
) {
    val divider = "  •  "
    val tagDivider = "  "
    val text = buildAnnotatedString {
        append(vehicle.metadata.type.toString())
        append(divider)
        append(vehicle.metadata.country)
        append(divider)
        append(vehicle.metadata.registrationYear)
        append(divider)
        append(vehicle.metadata.environmentalSticker.toString())
        append(divider)
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )
        withStyle(tagStyle) {
                append(" Sticker: ${vehicle.metadata.environmentalSticker.toString().uppercase(Locale.getDefault())} ")
            }
//        post.tags.forEachIndexed { index, tag ->
//            if (index != 0) {
//                append(tagDivider)
//            }
//            withStyle(tagStyle) {
//                append(" ${tag.uppercase(Locale.getDefault())} ")
//            }
//        }
    }
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        androidx.compose.material.Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(
    vehicle: Vehicle,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable { /* todo */ }
            .padding(vertical = 8.dp),
        icon = {
            Image(
                painter = painterResource(vehicle.imageThumbId),
                contentDescription = null,
                modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
            )
        },
        text = {
            Text(text = vehicle.name)
        },
        secondaryText = {
            PostMetadata(vehicle)
        }
    )
}

//@Composable
//@Preview
//fun VehiclesScreenPreview(){
//    VehiclesScreen()
//}