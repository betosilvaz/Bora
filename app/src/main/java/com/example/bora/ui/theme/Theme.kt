// Theme.kt
package com.example.bora.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ────────────────────────────────────────────
// Cores extras fora do esquema M3
// Acesse via: LocalBoraColors.current.mint
// ────────────────────────────────────────────
data class BoraExtendedColors(
    val orange: Color,
    val orangeLight: Color,
    val pink: Color,
    val mint: Color,
    val amber: Color,
)

val LocalBoraColors = staticCompositionLocalOf {
    BoraExtendedColors(
        orange      = BoraOrange,
        orangeLight = BoraOrangeLight,
        pink        = HotPink,
        mint        = ConfirmMint,
        amber       = HypeAmber,
    )
}

// ────────────────────────────────────────────
// Esquemas M3
// ────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary           = DarkPrimary,       // BoraOrangeMid — botão primário, FAB
    onPrimary         = DarkOnPrimary,
    secondary         = DarkSecondary,     // surface elevada — bottom sheet, nav bar
    onSecondary       = DarkOnSecondary,
    tertiary          = DarkTertiary,      // card, input field
    onTertiary        = DarkOnTertiary,

    outline           = DarkBorder,
    scrim             = DarkGlow,

    background        = DarkBackground,
    surface           = DarkSurface,
    onBackground      = DarkText,
    onSurface         = DarkText2,
    onSurfaceVariant  = DarkText3,
)

private val LightColorScheme = lightColorScheme(
    primary           = LightPrimary,      // BoraOrange — botão primário, FAB
    onPrimary         = LightOnPrimary,
    secondary         = LightSecondary,    // container de chip laranja suave
    onSecondary       = LightOnSecondary,
    tertiary          = LightTertiary,     // container de chip pink suave
    onTertiary        = LightOnTertiary,

    outline           = LightBorder,
    scrim             = LightShadow,

    background        = LightBackground,
    surface           = LightSurface,
    onBackground      = LightText,
    onSurface         = LightText2,
    onSurfaceVariant  = LightText3,
)

@Composable
fun BoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalBoraColors provides LocalBoraColors.current) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            content     = content,
        )
    }
}