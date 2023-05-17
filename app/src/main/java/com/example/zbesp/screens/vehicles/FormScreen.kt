package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.Validator
import ch.benlu.composeform.fields.*
import ch.benlu.composeform.formatters.dateShort
import ch.benlu.composeform.validators.MinLengthValidator
import ch.benlu.composeform.validators.NotEmptyValidator
import com.example.zbesp.R
import com.example.zbesp.data.*
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.showDialog
import com.example.zbesp.screens.userEmail
import com.example.zbesp.screens.zones.connectivityEnabled
import com.example.zbesp.ui.theme.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class FormScreen : Form() {
    override fun self(): Form {
        return this
    }

    fun checkFields(): Boolean {
        return username.isValid.value!! && country.isValid.value!! && vehicleType.isValid.value!!
                && environmentalSticker.isValid.value!! && registrationYear.isValid.value!!
    }

    @FormField
    val username = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(
            NotEmptyValidator(), MinLengthValidator(
                4,
                "The name must be at least 4 characters"
            )
        )
    )

    @FormField
    val country = FieldState(
        state = mutableStateOf<Country?>(null),
        options = mutableListOf(
            Country(CountryEnum.Spain)
        ),
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val vehicleType = FieldState(
        state = mutableStateOf<VehicleType?>(null),
        options = mutableListOf(
            VehicleType(VehicleTypeEnum.PrivateCar, R.drawable.private_car, R.drawable.private_car_white),
            VehicleType(VehicleTypeEnum.MotorHome, R.drawable.motor_home, R.drawable.motor_home_white),
            VehicleType(VehicleTypeEnum.Truck, R.drawable.truck, R.drawable.truck_white),
            VehicleType(VehicleTypeEnum.MotorBike, R.drawable.motor_bike, R.drawable.motor_bike_white),
            VehicleType(VehicleTypeEnum.Bus, R.drawable.bus, R.drawable.bus_white),
            VehicleType(VehicleTypeEnum.Van, R.drawable.van, R.drawable.van_white),
            VehicleType(VehicleTypeEnum.Tractor, R.drawable.tractor, R.drawable.tractor_white),
        ),
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val environmentalSticker = FieldState(
        state = mutableStateOf<EnvironmentalSticker?>(null),
        options = mutableListOf(
            EnvironmentalSticker(EnvironmentalStickerEnum.Zero, R.drawable.pegatinazero),
            EnvironmentalSticker(EnvironmentalStickerEnum.ECO, R.drawable.pegatinaeco),
            EnvironmentalSticker(EnvironmentalStickerEnum.C, R.drawable.pegatinac),
            EnvironmentalSticker(EnvironmentalStickerEnum.B, R.drawable.pegatinab),
            EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone),
        ),
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val registrationYear = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(
            NotEmptyValidator(),
            PreviousDateValidator(
                { Calendar.getInstance().time.time },
                "Date should not be in the future"
            )
        )
    )

    @FormField
    val enableVehicle = FieldState(
        state = mutableStateOf<Boolean?>(false)
    )

}

// TODO Move to another folder
class PreviousDateValidator(minDateTime: () -> Long, errorText: String? = null) : Validator<Date?>(
    validate = {
        (it?.time ?: -1) < minDateTime()
    },
    errorText = errorText ?: "This field is not valid."
)

class MainViewModel : ViewModel() {
    var form = FormScreen()
}

// TODO change color in some fields in dark mode
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FormScreen(viewModel: MainViewModel, navController: NavController, context: Context) {
    val error = remember { mutableStateOf(false) }
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.form_screen_title), navController) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.padding(20.dp))
                TitleText(
                    text = stringResource(id = R.string.create_your_vehicle),
                    alignment = TextAlign.Justify,
                    style = MaterialTheme.typography.h5
                )
            }
            item {
                Spacer(modifier = Modifier.padding(20.dp))
                TextField(
                    label = stringResource(id = R.string.vehicle_name),
                    form = viewModel.form,
                    fieldState = viewModel.form.username,
                ).Field()
            }
            item {
                DateField(
                    label = stringResource(id = R.string.registration_year),
                    form = viewModel.form,
                    fieldState = viewModel.form.registrationYear,
                    formatter = ::dateShort
                ).Field()
            }
            item {
                PickerField(
                    label = stringResource(id = R.string.country),
                    form = viewModel.form,
                    fieldState = viewModel.form.country
                ).Field()
            }
            item {
                PickerField(
                    label = stringResource(id = R.string.type),
                    form = viewModel.form,
                    fieldState = viewModel.form.vehicleType
                ).Field()
            }
            item {
                PickerField(
                    label = stringResource(id = R.string.environmental_sticker),
                    form = viewModel.form,
                    fieldState = viewModel.form.environmentalSticker
                ).Field()
            }
            item {
                CheckboxField(
                    fieldState = viewModel.form.enableVehicle,
                    label = stringResource(id = R.string.mark_current_vehicle),
                    form = viewModel.form
                ).Field()
            }
            item {
                Button(
                    onClick = {
                        if (connectivityEnabled()) {
                            if (viewModel.form.checkFields()) {
                                if (vehicles.value.isEmpty()) {
                                    viewModel.form.enableVehicle.state.value = true
                                }
                                var id = 0L
                                Log.i("getIdFromDatabase", "pre")
                                idDatabase.document(userEmail).get().addOnSuccessListener {
                                    id = it["id"] as Long
                                    addVehicleToDatabase(id, context, viewModel)
                                    idDatabase.document(userEmail).update("id", FieldValue.increment(1))
                                    navController.popBackStack()
                                }
                                //val currentId = getIdFromDatabase()
                                //Log.i("currentId", currentId.toString())

                            } else {
                                error.value = true
                            }
                        } else {
                            showDialog(context, context.getString(R.string.network_error) )
                        }

                    },
                    colors = getButtonColorsReversed(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) {
                    TopBarTittle(stringResource(id = R.string.create_vehicle), TextAlign.Justify)
                }
            }
            item {
                if (error.value) {
                    TitleTextRed(stringResource(id = R.string.fields_not_empty), TextAlign.Justify)
                }
            }
        }
    }
}

private fun addVehicleToDatabase(id: Long ,context: Context, viewModel: MainViewModel) {
    val newVehicle = Vehicle(
        userEmail.hashCode().toLong() + id + 1L,
        viewModel.form.username.state.value!!,
        viewModel.form.country.state.value!!.type!!.name,
        viewModel.form.vehicleType.state.value!!.type!!.name,
        viewModel.form.registrationYear.state.value!!,
        viewModel.form.environmentalSticker.state.value!!.type!!.name,
        viewModel.form.enableVehicle.state.value!!,
        viewModel.form.environmentalSticker.state.value!!.stickerImage,
        viewModel.form.vehicleType.state.value!!.typeImage,
        viewModel.form.vehicleType.state.value!!.typeImageWhite,
        userEmail
    )
    vehiclesDatabase.add(newVehicle).addOnCompleteListener {
        if (it.isSuccessful) {
            Log.i("vehicleadded", "successful")
            if (newVehicle.enabled) {
                noEnabledVehicleInDatabase(newVehicle)
            }
        } else {
            Log.i("vehicleadded", "error")
            showDialog(context= context, "Vehicle cloud not be created")
        }
    }
}

