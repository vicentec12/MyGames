package br.com.vicentec12.mygames.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion

private val darkColorScheme = darkColorScheme(
    primary = primary,
    primaryContainer = primary,
    secondary = secondary
)

private val lightColorScheme = lightColorScheme(
    primary = primary,
    primaryContainer = primary,
    secondary = secondary
)

@Composable
fun MyGamesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    val colors = lightColorScheme

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}