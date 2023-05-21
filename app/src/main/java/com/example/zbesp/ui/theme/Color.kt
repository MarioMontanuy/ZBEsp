package com.example.zbesp.ui.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SapphireBlue = Color(0xFF0F52BA)
val SapphireBlueTransparent = Color(0xBF0F52BA)
val errorColor = Color(0xFFE61414)
@Composable
fun getButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = SapphireBlue,
        disabledBackgroundColor = Color.White,
        disabledContentColor = SapphireBlue
    )
}

@Composable
fun getButtonColorsReversed(): ButtonColors {
    return ButtonDefaults.buttonColors(
        backgroundColor = SapphireBlue,
        contentColor = Color.White,
        disabledBackgroundColor = SapphireBlue,
        disabledContentColor = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getOutlinedTextFieldColors(): TextFieldColors{
    return TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = SapphireBlue
    )
}