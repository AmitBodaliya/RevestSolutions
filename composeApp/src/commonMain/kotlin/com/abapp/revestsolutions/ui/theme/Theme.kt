package com.abapp.revestsolutions.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// -----------------------------
// 🎨 Color Definitions
// -----------------------------

private val lightScheme =
  lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
  )

private val darkScheme =
  darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
  )





// -----------------------------
// 🌈 Custom App Colors
// -----------------------------
@Immutable
data class CustomColors(
  val success: Color = Color(0xFF4CAF50),
  val warning: Color = Color(0xFFFFC107),
  val info: Color = Color(0xFF2196F3),
  val danger: Color = Color(0xFFF44336),
)

val LocalCustomColors = staticCompositionLocalOf { CustomColors() }

val lightCustomColors = CustomColors(
  success = Color(0xFF4CAF50),
  warning = Color(0xFFFFC107),
  info = Color(0xFF3174F1),
  danger = Color(0xFFE53935)
)

val darkCustomColors = CustomColors(
  success = Color(0xFF66BB6A),
  warning = Color(0xFFFFD54F),
  info = Color(0xFF90CAF9),
  danger = Color(0xFFEF5350)
)




// -----------------------------
// ✍️ Typography (you can extend later)
// -----------------------------
val AppTypography = Typography()






// -----------------------------
// 🎭 Theme Composable
// -----------------------------
@Composable
fun RevestSolutionsTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) darkScheme else lightScheme
  val customColors = if (darkTheme) darkCustomColors else lightCustomColors


  CompositionLocalProvider(LocalCustomColors provides customColors) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = AppTypography,
      content = content
    )
  }
}




// -----------------------------
// 🌍 Accessor for custom colors
// -----------------------------
val MaterialTheme.customColors: CustomColors
  @Composable
  @ReadOnlyComposable
  get() = LocalCustomColors.current






@Composable
internal expect fun SystemAppearance(isDark: Boolean)
