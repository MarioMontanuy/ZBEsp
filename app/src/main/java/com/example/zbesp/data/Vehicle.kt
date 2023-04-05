package com.example.zbesp.data

import androidx.annotation.DrawableRes
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.zbesp.ui.theme.Turquoise
import com.example.zbesp.ui.theme.Blue
import com.example.zbesp.R
@Immutable
data class Vehicle(
    val id: Long,
    val name: String,
    val route: String,
    val metadata: Metadata,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int,
)

@Immutable
data class Metadata(
    val country: String,
    val type: VehicleType,
    val registrationYear: String,
    val environmentalSticker: EnvironmentalSticker,
    val stickerColor: Color,
    val enabled: Boolean
)

//@Immutable
//data class PostAuthor(
//    val name: String,
//    val url: String? = null
//)


//private val pietro = PostAuthor("Pietro Maggi", "https://medium.com/@pmaggi")

private val vehicleNone = Vehicle(
    id = 1L,
    name = "Car1",
    route = "Car1",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.None,
        stickerColor = Color.Gray,
        enabled = true
    ),
    imageId = R.drawable.vehicle,
    imageThumbId = R.drawable.vehicle,
)

private val vehicleB = Vehicle(
    id = 2L,
    name = "Car3",
    route = "Car3",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.B,
        stickerColor = Color.Yellow,
        enabled = false
    ),
    imageId = R.drawable.vehicle,
    imageThumbId = R.drawable.vehicle,
)

private val vehicleC = Vehicle(
    id = 3L,
    name = "Car2",
    route = "Car2",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.C,
        stickerColor = Color.Green,
        enabled = false
    ),
    imageId = R.drawable.vehicle,
    imageThumbId = R.drawable.vehicle,
)

private val vehicleECO = Vehicle(
    id = 4L,
    name = "Car4",
    route = "Car4",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.ECO,
        stickerColor = Turquoise,
        enabled = false
    ),
    imageId = R.drawable.vehicle,
    imageThumbId = R.drawable.vehicle,
)

private val vehicleZero = Vehicle(
    id = 5L,
    name = "Car4",
    route = "Car4",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.Zero,
        stickerColor = Blue,
        enabled = false
    ),
    imageId = R.drawable.vehicle,
    imageThumbId = R.drawable.vehicle,
)

object VehiclesRepo {
    fun getVehicles(): List<Vehicle> = vehicles
    fun getFeaturedPost(): Vehicle = vehicles.random()
}

private val vehicles = listOf(
    vehicleNone,
    vehicleB,
    vehicleC,
    vehicleECO,
    vehicleZero,
    vehicleNone.copy(id = 9L),
    vehicleNone.copy(id = 10L),
    vehicleNone.copy(id = 11L),
    vehicleNone.copy(id = 12L),
    vehicleNone.copy(id = 13L),
    vehicleNone.copy(id = 14L),
    vehicleNone.copy(id = 15L),
    vehicleNone.copy(id = 16L),
)

enum class VehicleType (type: String) {
    PrivateCar("Private Car"),
    MotorHome("Motor Home"),
    Truck("Truck"),
    MotorBike("Motor Bike"),
    Bus("Bus"),
    Van("Van"),
    Tractor("Tractor");
}

enum class EnvironmentalSticker (type: String) {
    Zero("Zero"),
    ECO("ECO"),
    C("C"),
    B("B"),
    None("None");
}

fun getVehicle(vehicleId: String): Vehicle{
    return vehicles.first {it.id == vehicleId.toLong()}
}