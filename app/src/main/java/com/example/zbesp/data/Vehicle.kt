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
import java.util.*

@Immutable
data class Vehicle(
    val id: Long,
    val name: String,
    val country: Country,
    val type: VehicleType,
    val registrationYear: Date,
    val environmentalSticker: EnvironmentalSticker,
    var enabled: Boolean,
    @DrawableRes var imageId: Int = R.drawable.private_car,
) {


    fun setImage(vehicle: Vehicle, type: VehicleType){
        if (type.type == VehicleTypeEnum.PrivateCar) {
            vehicle.imageId = R.drawable.private_car
        }
        if (type.type == VehicleTypeEnum.MotorHome) {
            vehicle.imageId = R.drawable.motor_home
        }
        if (type.type == VehicleTypeEnum.Truck) {
            vehicle.imageId = R.drawable.truck
        }
        if (type.type == VehicleTypeEnum.MotorBike) {
            vehicle.imageId = R.drawable.motor_bike
        }
        if (type.type == VehicleTypeEnum.Bus) {
            vehicle.imageId = R.drawable.bus
        }
        if (type.type == VehicleTypeEnum.Van) {
            vehicle.imageId = R.drawable.van
        }
        if (type.type == VehicleTypeEnum.Tractor) {
            vehicle.imageId = R.drawable.tractor
        }
    }
}

//@Immutable
//data class VehicleMetadata(
//
//)


data class VehicleType(val type: VehicleTypeEnum?) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

data class EnvironmentalSticker(val type: EnvironmentalStickerEnum?) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

data class Country(val type: CountryEnum?) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

//private val pietro = PostAuthor("Pietro Maggi", "https://medium.com/@pmaggi")

val vehicleNone: Vehicle =
    Vehicle(id = 1L,
    name = "Car1",
    country = Country(CountryEnum.Spain),
    type = VehicleType(VehicleTypeEnum.PrivateCar),
    registrationYear = Date(101,5,5) ,
    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.None),
    enabled = true,
    imageId = R.drawable.private_car,
    )

private val vehicleB: Vehicle = Vehicle(
    id = 2L,
    name = "Car2",
    country = Country(CountryEnum.Spain),
    type = VehicleType(VehicleTypeEnum.PrivateCar),
    registrationYear = Date(101,5,5) ,
    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.B),
    enabled = false,
    imageId = R.drawable.private_car,
)


private val vehicleC: Vehicle = Vehicle(
    id = 3L,
    name = "Car3",
    country = Country(CountryEnum.Spain),
    type = VehicleType(VehicleTypeEnum.PrivateCar),
    registrationYear = Date(101,5,5) ,
    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.C),
    enabled = false,
    imageId = R.drawable.private_car,
)

private val vehicleECO: Vehicle = Vehicle(
    id = 4L,
    name = "Car4",
    country = Country(CountryEnum.Spain),
    type = VehicleType(VehicleTypeEnum.PrivateCar),
    registrationYear = Date(101,5,5) ,
    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.ECO),
    enabled = false,
    imageId = R.drawable.private_car,
)

private val vehicleZero: Vehicle = Vehicle(
    id = 5L,
    name = "Car5",
    country = Country(CountryEnum.Spain),
    type = VehicleType(VehicleTypeEnum.PrivateCar),
    registrationYear = Date(101,5,5) ,
    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.Zero),
    enabled = false,
    imageId = R.drawable.private_car,
)

object VehiclesRepo {
    fun getVehicles(): List<Vehicle> = vehicles
}

fun noEnabledVehicle(){
    vehicles.forEach {
        vehicle -> vehicle.enabled = false
    }
}

var currentId: Long = 6L

var vehicles: List<Vehicle> =
    listOf(vehicleNone,
        vehicleB,
        vehicleC,
        vehicleECO,
        vehicleZero,
)

enum class CountryEnum (type: String) {
    Spain("Spain"),
}

enum class VehicleTypeEnum (type: String) {
    PrivateCar("Private Car"),
    MotorHome("Motor Home"),
    Truck("Truck"),
    MotorBike("Motor Bike"),
    Bus("Bus"),
    Van("Van"),
    Tractor("Tractor");
}

enum class EnvironmentalStickerEnum (type: String) {
    Zero("Zero"),
    ECO("ECO"),
    C("C"),
    B("B"),
    None("None");
}

fun getVehicle(vehicleId: String): Vehicle{
    return vehicles.first {it.id == vehicleId.toLong()}
}