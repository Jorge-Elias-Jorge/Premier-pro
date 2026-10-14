package com.premierpro.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Background = Color(0xFF08080D)
val Surface = Color(0xFF12121A)
val SurfaceHigh = Color(0xFF1D1D28)
val Accent = Color(0xFF7C5CFF)
val TextPrimary = Color(0xFFF3F3F6)
val TextMuted = Color(0xFF9A9AA8)
val Danger = Color(0xFFFF5470)

private val DarkColors = darkColorScheme(
    primary = Accent,
    background = Background,
    surface = Surface,
    surfaceVariant = SurfaceHigh,
    onPrimary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextMuted,
    error = Danger
)

@Composable
fun PremierProTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}
