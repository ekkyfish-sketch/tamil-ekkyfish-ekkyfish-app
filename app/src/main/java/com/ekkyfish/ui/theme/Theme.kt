package com.ekkyfish.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EkkyFishColorScheme = lightColorScheme(
    primary = Color(0xFFFF6B35),
    secondary = Color(0xFF1E3A5F),
    background = Color(0xFFF8F8F8),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF111111),
    onSurface = Color(0xFF111111)
)

@Composable
fun EkkyFishTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EkkyFishColorScheme,
        content = content
    )
}
