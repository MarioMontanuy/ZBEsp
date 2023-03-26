package com.example.zbesp.data

import android.os.Environment
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.example.zbesp.R

@Immutable
data class Vehicle(
    val id: Long,
    val name: String,
    val metadata: Metadata,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int,
)

@Immutable
data class Metadata(
    val country: String,
    val type: VehicleType,
    val registrationYear: String,
    val environmentalSticker: EnvironmentalSticker
)

//@Immutable
//data class PostAuthor(
//    val name: String,
//    val url: String? = null
//)


//private val pietro = PostAuthor("Pietro Maggi", "https://medium.com/@pmaggi")

private val vehicle1 = Vehicle(
    id = 1L,
    name = "Car1",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.None
    ),
    imageId = R.drawable.ic_launcher_background,
    imageThumbId = R.drawable.ic_launcher_background,
)

private val vehicle2 = Vehicle(
    id = 1L,
    name = "Car2",
    metadata = Metadata(
        country = "Spain",
        type = VehicleType.PrivateCar,
        registrationYear = "2001",
        environmentalSticker = EnvironmentalSticker.None
    ),
    imageId = R.drawable.ic_launcher_background,
    imageThumbId = R.drawable.ic_launcher_background,
)
object VehiclesRepo {
    fun getVehicles(): List<Vehicle> = vehicles
    fun getFeaturedPost(): Vehicle = vehicles.random()
}

private val vehicles = listOf(
    vehicle1,
    vehicle2,
    vehicle1.copy(id = 6L),
    vehicle1.copy(id = 7L),
    vehicle1.copy(id = 8L),
    vehicle1.copy(id = 9L),
    vehicle1.copy(id = 10L),
    vehicle1.copy(id = 11L),
    vehicle1.copy(id = 12L),
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

