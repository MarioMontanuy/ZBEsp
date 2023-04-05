package com.example.zbesp.screens.vehicles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zbesp.data.Vehicle
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.formTextFieldColors


@Composable
fun NewVehicleDetailScreen(){
    Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
        AddTextFieldRow("Enter name")
        AddTextFieldRow("Enter country")
        AddTextFieldRow("Enter Registration Year")
        AddTextFieldRow("Enter Type")
        AddTextFieldRow("Enter Environmental Sticker")
    }
}
@Composable
fun AddTextFieldRow(label: String){
    var textState by remember { mutableStateOf("") }

    TextField(
        value = textState,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { textState = it },
        label = { TitleText(label, TextAlign.Start)},
        colors = formTextFieldColors()
    )
}




