package com.example.ui.theme

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

// Arso Pro Dark Theme Color Palette
private val ArsoDarkColors = darkColorScheme(
    primary = Color(0xFF38BDF8), // Radiant Sky Blue
    onPrimary = Color(0xFF00324D),
    primaryContainer = Color(0xFF004D74),
    onPrimaryContainer = Color(0xFFBFE5FF),
    
    secondary = Color(0xFF818CF8), // Deep Violet
    onSecondary = Color(0xFF1B1B4D),
    secondaryContainer = Color(0xFF2E2E74),
    onSecondaryContainer = Color(0xFFE0E0FF),
    
    tertiary = Color(0xFFF472B6), // Soft Magenta Pink
    
    background = Color(0xFF090D16), // Midnight Cadet Indigo
    onBackground = Color(0xFFF1F5F9),
    
    surface = Color(0xFF111827), // Sleek Matte Charcoal
    onSurface = Color(0xFFF9FAFB),
    
    surfaceVariant = Color(0xFF1F2937),
    onSurfaceVariant = Color(0xFFD1D5DB),
    
    outline = Color(0xFF9CA3AF)
)

private val ArsoLightColors = lightColorScheme(
    primary = Color(0xFF0284C7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = Color(0xFF0369A1),
    
    secondary = Color(0xFF4F46E5),
    onSecondary = Color.White,
    
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to deep cinematic dark theme!
    dynamicColor: Boolean = false, // Keep consistent branding design
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) ArsoDarkColors else ArsoLightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
