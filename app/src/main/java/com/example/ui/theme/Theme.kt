package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LuminaDarkColorScheme = darkColorScheme(
    primary = LuminaCyan,
    onPrimary = LuminaBackground,
    primaryContainer = LuminaSurfaceVariant,
    onPrimaryContainer = LuminaCyan,
    secondary = LuminaPurple,
    onSecondary = LuminaBackground,
    secondaryContainer = LuminaSurfaceVariant,
    onSecondaryContainer = LuminaPurple,
    tertiary = LuminaGold,
    onTertiary = LuminaBackground,
    background = LuminaBackground,
    onBackground = LuminaTextPrimary,
    surface = LuminaSurface,
    onSurface = LuminaTextPrimary,
    surfaceVariant = LuminaSurfaceVariant,
    onSurfaceVariant = LuminaTextSecondary,
    outline = LuminaBorder,
    error = LuminaRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false, // Preserve Lumina cyberpunk styling
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LuminaDarkColorScheme,
        typography = Typography,
        content = content
    )
}
