package com.example.zbesp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@Composable
fun TitleText(text: String, alignment: TextAlign, style: TextStyle) {
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = style,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = style,
            textAlign = alignment
        )
    }
}

@Composable
fun BigTitleText(text: String, alignment: TextAlign) {
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            textAlign = alignment
        )
    }
}

@Composable
fun TitleText(text: String, alignment: TextAlign) {
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = alignment
        )
    }
}

@Composable
fun TopBarTittle(text: String, alignment: TextAlign) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1,
        textAlign = alignment
    )
}

@Composable
fun TitleTextRed(text: String, alignment: TextAlign) {
    Text(
        text = text,
        color = errorColor,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1,
        textAlign = alignment
    )
}

@Composable
fun SubtitleText(text: String, alignment: TextAlign, style: TextStyle) {
    Text(
        text = text,
        style = style,
        textAlign = alignment
    )
}

@Composable
fun SubtitleText(text: String, alignment: TextAlign) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        textAlign = alignment
    )
}
