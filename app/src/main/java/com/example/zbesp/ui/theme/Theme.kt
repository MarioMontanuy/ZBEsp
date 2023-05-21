package com.example.zbesp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.*
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColors(
    surface = SapphireBlue,
    primary = SapphireBlue,
    primaryVariant = SapphireBlue,
    secondary = SapphireBlue
)

val LightColorScheme = lightColors(
    primary = SapphireBlue,
    primaryVariant = SapphireBlue,
    secondary = SapphireBlue,
)

@Composable
fun ZBEspTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = MaterialTheme.typography,
        shapes = Shapes,
        content = content
    )
}

