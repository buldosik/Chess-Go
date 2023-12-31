package com.example.chessgo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme  = darkColorScheme(
    primary = AmethystPrimary,
    onPrimary = Platinum,
    secondary = AmethystSecondary,
    onSecondary = Platinum,
    tertiary = Flame,
    onTertiary = FlameBrighter,
    background = RaisinBlack,
    onBackground = Platinum,
    /*
    primary = ,
    onPrimary = ,
    primaryContainer = ,
    onPrimaryContainer = ,
    inversePrimary = ,
    secondary = ,
    onSecondary = ,
    secondaryContainer = ,
    onSecondaryContainer = ,
    tertiary = ,
    onTertiary = ,
    tertiaryContainer = ,
    onTertiaryContainer = ,
    error = ,
    onError = ,
    errorContainer = ,
    onErrorContainer = ,
    background = ,
    onBackground = ,
    surface = ,
    onSurface = ,
    inverseSurface = ,
    inverseOnSurface = ,
    surfaceVariant = ,
    onSurfaceVariant = ,
    outline =
    */
)

private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200,
)

@Composable
fun ChessgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}