package ci.nsu.moble.main.ui.theme

import android.app.Activity
import android.graphics.Color.red
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(red = 79, green = 218, blue = 218),
    secondary = Color(red = 176, green = 204, blue = 204),
    tertiary = Color(red = 23, green = 29, blue = 29)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(red = 0, green = 106, blue = 106),
    secondary = Color(red = 74, green = 99, blue = 99),
    tertiary = Color(red = 245, green = 250, blue = 250)
)

@Composable
fun PracticeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography, // используем типографику по умолчанию
        content = content
    )
}