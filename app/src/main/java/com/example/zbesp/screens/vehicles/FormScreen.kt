package com.example.zbesp.screens.vehicles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.fields.DateField
import ch.benlu.composeform.fields.PasswordField
import ch.benlu.composeform.fields.PickerField
import ch.benlu.composeform.fields.TextField
import ch.benlu.composeform.formatters.dateShort
import ch.benlu.composeform.validators.DateValidator
import ch.benlu.composeform.validators.MinLengthValidator
import ch.benlu.composeform.validators.NotEmptyValidator
import com.example.zbesp.data.VehicleType
import java.util.*

class FormScreen: Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val username = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val vehicleType = FieldState(
        state = mutableStateOf<VehicleType?>(null),
        // define your selectable options (countries)
        options = mutableListOf(
            VehicleType.PrivateCar,
            VehicleType.MotorHome,
            VehicleType.Truck,
            VehicleType.MotorBike,
            VehicleType.Bus,
            VehicleType.Van,
            VehicleType.Tractor,
        ),
        // format each option with the following lambda function
        optionItemFormatter = { "${it?.name}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val registrationYear = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(NotEmptyValidator())
    )



}

class MainViewModel : ViewModel() {
    var form = FormScreen()
}

@Composable
fun SingleTextField(viewModel: MainViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            label = "Username",
            form = viewModel.form,
            fieldState = viewModel.form.username,
        ).Field()


        PickerField(
            label = "Country",
            form = viewModel.form,
            fieldState = viewModel.form.vehicleType
        ).Field()

        DateField(
            label = "Start Date",
            form = viewModel.form,
            fieldState = viewModel.form.registrationYear,
            formatter = ::dateShort
        ).Field()


    }
}