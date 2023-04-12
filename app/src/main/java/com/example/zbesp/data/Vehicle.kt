package com.example.zbesp.data

import androidx.annotation.DrawableRes
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import ch.benlu.composeform.fields.PickerValue
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
    var enabled: Boolean
)


data class VehicleType(val type: String) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type.startsWith(query)
    }

    fun self(): String? {
        VehicleTypeEnum.values().forEach { value -> if (value.name == type) {
            return value.name }
        }
        return null
    }
}
//private val pietro = PostAuthor("Pietro Maggi", "https://medium.com/@pmaggi")

val vehicleNone: Vehicle =
    Vehicle(id = 1L,
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

private val vehicleB: Vehicle = Vehicle(
    id = 2L,
    name = "Car2",
    route = "Car2",
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


private val vehicleC: Vehicle = Vehicle(
    id = 3L,
    name = "Car3",
    route = "Car3",
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

private val vehicleECO: Vehicle = Vehicle(
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

private val vehicleZero: Vehicle = Vehicle(
    id = 5L,
    name = "Car5",
    route = "Car5",
    metadata = Metadata(
        country = "Spain",
        type = V,
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
}

fun noEnabledVehicle(){
    vehicles.forEach {
        vehicle -> vehicle.metadata.enabled = false
    }
}

var vehicles: List<Vehicle> =
    listOf(vehicleNone,
        vehicleB,
        vehicleC,
        vehicleECO,
        vehicleZero,
)


enum class VehicleTypeEnum (type: String) {
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