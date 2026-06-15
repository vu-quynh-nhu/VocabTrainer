package com.example.vocabtrainer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GreenMain,
    secondary = GreenSecondary,
    tertiary = LightGreen
)

@Composable
fun VocabTrainerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}