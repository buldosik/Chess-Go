package com.example.chessgo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme  = darkColorScheme(
    primary = Amethyst,
    onPrimary = Platinum,
    secondary = Flame,
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
    val ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}