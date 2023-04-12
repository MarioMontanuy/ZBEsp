package com.example.zbesp.screens.vehicles

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.fields.TextField
import ch.benlu.composeform.validators.MinLengthValidator

class FormScreen: Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val name = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(
            MinLengthValidator(4, "The name must be at least 4 characters")
        )
    )

}

class MainViewModel : ViewModel() {
    var form = FormScreen()
}

@Composable
fun SingleTextField(viewModel: MainViewModel) {
    Column {
        TextField(
            label = "Name",
            form = viewModel.form,
            fieldState = viewModel.form.name,
        ).Field()
    }
}