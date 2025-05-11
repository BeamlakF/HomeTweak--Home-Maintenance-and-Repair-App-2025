package com.example.myapplication.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SafetyOrange,           // Used for Button background, toggle switches, etc.
    onPrimary = Color.White,          // Text color on primary (e.g. on orange buttons)

    secondary = MutedGrey,            // Secondary UI elements
    onSecondary = Color.White,        // Text on secondary background

    background = OffWhite,     // General screen background
    onBackground = DarkBlue,          // Text on background

    surface = CardColor,         // Cards, sheets, menus
    onSurface = DarkBlue              // Text on cards
)

@Composable
fun myapplicationtheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography, // you can customize this too
        content = content
    )
}
