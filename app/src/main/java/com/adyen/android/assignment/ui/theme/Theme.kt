package com.adyen.android.assignment.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.adyen.android.assignment.ui.theme.backgroundDark
import com.adyen.android.assignment.ui.theme.backgroundDarkHighContrast
import com.adyen.android.assignment.ui.theme.backgroundDarkMediumContrast
import com.adyen.android.assignment.ui.theme.backgroundLight
import com.adyen.android.assignment.ui.theme.backgroundLightHighContrast
import com.adyen.android.assignment.ui.theme.backgroundLightMediumContrast
import com.adyen.android.assignment.ui.theme.errorContainerDark
import com.adyen.android.assignment.ui.theme.errorContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.errorContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.errorContainerLight
import com.adyen.android.assignment.ui.theme.errorContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.errorContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.errorDark
import com.adyen.android.assignment.ui.theme.errorDarkHighContrast
import com.adyen.android.assignment.ui.theme.errorDarkMediumContrast
import com.adyen.android.assignment.ui.theme.errorLight
import com.adyen.android.assignment.ui.theme.errorLightHighContrast
import com.adyen.android.assignment.ui.theme.errorLightMediumContrast
import com.adyen.android.assignment.ui.theme.inverseOnSurfaceDark
import com.adyen.android.assignment.ui.theme.inverseOnSurfaceDarkHighContrast
import com.adyen.android.assignment.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.adyen.android.assignment.ui.theme.inverseOnSurfaceLight
import com.adyen.android.assignment.ui.theme.inverseOnSurfaceLightHighContrast
import com.adyen.android.assignment.ui.theme.inverseOnSurfaceLightMediumContrast
import com.adyen.android.assignment.ui.theme.inversePrimaryDark
import com.adyen.android.assignment.ui.theme.inversePrimaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.inversePrimaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.inversePrimaryLight
import com.adyen.android.assignment.ui.theme.inversePrimaryLightHighContrast
import com.adyen.android.assignment.ui.theme.inversePrimaryLightMediumContrast
import com.adyen.android.assignment.ui.theme.inverseSurfaceDark
import com.adyen.android.assignment.ui.theme.inverseSurfaceDarkHighContrast
import com.adyen.android.assignment.ui.theme.inverseSurfaceDarkMediumContrast
import com.adyen.android.assignment.ui.theme.inverseSurfaceLight
import com.adyen.android.assignment.ui.theme.inverseSurfaceLightHighContrast
import com.adyen.android.assignment.ui.theme.inverseSurfaceLightMediumContrast
import com.adyen.android.assignment.ui.theme.onBackgroundDark
import com.adyen.android.assignment.ui.theme.onBackgroundDarkHighContrast
import com.adyen.android.assignment.ui.theme.onBackgroundDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onBackgroundLight
import com.adyen.android.assignment.ui.theme.onBackgroundLightHighContrast
import com.adyen.android.assignment.ui.theme.onBackgroundLightMediumContrast
import com.adyen.android.assignment.ui.theme.onErrorContainerDark
import com.adyen.android.assignment.ui.theme.onErrorContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.onErrorContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onErrorContainerLight
import com.adyen.android.assignment.ui.theme.onErrorContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.onErrorContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.onErrorDark
import com.adyen.android.assignment.ui.theme.onErrorDarkHighContrast
import com.adyen.android.assignment.ui.theme.onErrorDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onErrorLight
import com.adyen.android.assignment.ui.theme.onErrorLightHighContrast
import com.adyen.android.assignment.ui.theme.onErrorLightMediumContrast
import com.adyen.android.assignment.ui.theme.onPrimaryContainerDark
import com.adyen.android.assignment.ui.theme.onPrimaryContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.onPrimaryContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onPrimaryContainerLight
import com.adyen.android.assignment.ui.theme.onPrimaryContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.onPrimaryContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.onPrimaryDark
import com.adyen.android.assignment.ui.theme.onPrimaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.onPrimaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onPrimaryLight
import com.adyen.android.assignment.ui.theme.onPrimaryLightHighContrast
import com.adyen.android.assignment.ui.theme.onPrimaryLightMediumContrast
import com.adyen.android.assignment.ui.theme.onSecondaryContainerDark
import com.adyen.android.assignment.ui.theme.onSecondaryContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.onSecondaryContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onSecondaryContainerLight
import com.adyen.android.assignment.ui.theme.onSecondaryContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.onSecondaryContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.onSecondaryDark
import com.adyen.android.assignment.ui.theme.onSecondaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.onSecondaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onSecondaryLight
import com.adyen.android.assignment.ui.theme.onSecondaryLightHighContrast
import com.adyen.android.assignment.ui.theme.onSecondaryLightMediumContrast
import com.adyen.android.assignment.ui.theme.onSurfaceDark
import com.adyen.android.assignment.ui.theme.onSurfaceDarkHighContrast
import com.adyen.android.assignment.ui.theme.onSurfaceDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onSurfaceLight
import com.adyen.android.assignment.ui.theme.onSurfaceLightHighContrast
import com.adyen.android.assignment.ui.theme.onSurfaceLightMediumContrast
import com.adyen.android.assignment.ui.theme.onSurfaceVariantDark
import com.adyen.android.assignment.ui.theme.onSurfaceVariantDarkHighContrast
import com.adyen.android.assignment.ui.theme.onSurfaceVariantDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onSurfaceVariantLight
import com.adyen.android.assignment.ui.theme.onSurfaceVariantLightHighContrast
import com.adyen.android.assignment.ui.theme.onSurfaceVariantLightMediumContrast
import com.adyen.android.assignment.ui.theme.onTertiaryContainerDark
import com.adyen.android.assignment.ui.theme.onTertiaryContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.onTertiaryContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onTertiaryContainerLight
import com.adyen.android.assignment.ui.theme.onTertiaryContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.onTertiaryContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.onTertiaryDark
import com.adyen.android.assignment.ui.theme.onTertiaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.onTertiaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.onTertiaryLight
import com.adyen.android.assignment.ui.theme.onTertiaryLightHighContrast
import com.adyen.android.assignment.ui.theme.onTertiaryLightMediumContrast
import com.adyen.android.assignment.ui.theme.outlineDark
import com.adyen.android.assignment.ui.theme.outlineDarkHighContrast
import com.adyen.android.assignment.ui.theme.outlineDarkMediumContrast
import com.adyen.android.assignment.ui.theme.outlineLight
import com.adyen.android.assignment.ui.theme.outlineLightHighContrast
import com.adyen.android.assignment.ui.theme.outlineLightMediumContrast
import com.adyen.android.assignment.ui.theme.outlineVariantDark
import com.adyen.android.assignment.ui.theme.outlineVariantDarkHighContrast
import com.adyen.android.assignment.ui.theme.outlineVariantDarkMediumContrast
import com.adyen.android.assignment.ui.theme.outlineVariantLight
import com.adyen.android.assignment.ui.theme.outlineVariantLightHighContrast
import com.adyen.android.assignment.ui.theme.outlineVariantLightMediumContrast
import com.adyen.android.assignment.ui.theme.primaryContainerDark
import com.adyen.android.assignment.ui.theme.primaryContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.primaryContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.primaryContainerLight
import com.adyen.android.assignment.ui.theme.primaryContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.primaryContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.primaryDark
import com.adyen.android.assignment.ui.theme.primaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.primaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.primaryLight
import com.adyen.android.assignment.ui.theme.primaryLightHighContrast
import com.adyen.android.assignment.ui.theme.primaryLightMediumContrast
import com.adyen.android.assignment.ui.theme.scrimDark
import com.adyen.android.assignment.ui.theme.scrimDarkHighContrast
import com.adyen.android.assignment.ui.theme.scrimDarkMediumContrast
import com.adyen.android.assignment.ui.theme.scrimLight
import com.adyen.android.assignment.ui.theme.scrimLightHighContrast
import com.adyen.android.assignment.ui.theme.scrimLightMediumContrast
import com.adyen.android.assignment.ui.theme.secondaryContainerDark
import com.adyen.android.assignment.ui.theme.secondaryContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.secondaryContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.secondaryContainerLight
import com.adyen.android.assignment.ui.theme.secondaryContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.secondaryContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.secondaryDark
import com.adyen.android.assignment.ui.theme.secondaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.secondaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.secondaryLight
import com.adyen.android.assignment.ui.theme.secondaryLightHighContrast
import com.adyen.android.assignment.ui.theme.secondaryLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceBrightDark
import com.adyen.android.assignment.ui.theme.surfaceBrightDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceBrightDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceBrightLight
import com.adyen.android.assignment.ui.theme.surfaceBrightLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceBrightLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerDark
import com.adyen.android.assignment.ui.theme.surfaceContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighDark
import com.adyen.android.assignment.ui.theme.surfaceContainerHighDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighLight
import com.adyen.android.assignment.ui.theme.surfaceContainerHighLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighestDark
import com.adyen.android.assignment.ui.theme.surfaceContainerHighestDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighestLight
import com.adyen.android.assignment.ui.theme.surfaceContainerHighestLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerHighestLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLight
import com.adyen.android.assignment.ui.theme.surfaceContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowDark
import com.adyen.android.assignment.ui.theme.surfaceContainerLowDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowLight
import com.adyen.android.assignment.ui.theme.surfaceContainerLowLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowestDark
import com.adyen.android.assignment.ui.theme.surfaceContainerLowestDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowestLight
import com.adyen.android.assignment.ui.theme.surfaceContainerLowestLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceContainerLowestLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceDark
import com.adyen.android.assignment.ui.theme.surfaceDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceDimDark
import com.adyen.android.assignment.ui.theme.surfaceDimDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceDimDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceDimLight
import com.adyen.android.assignment.ui.theme.surfaceDimLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceDimLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceLight
import com.adyen.android.assignment.ui.theme.surfaceLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceLightMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceVariantDark
import com.adyen.android.assignment.ui.theme.surfaceVariantDarkHighContrast
import com.adyen.android.assignment.ui.theme.surfaceVariantDarkMediumContrast
import com.adyen.android.assignment.ui.theme.surfaceVariantLight
import com.adyen.android.assignment.ui.theme.surfaceVariantLightHighContrast
import com.adyen.android.assignment.ui.theme.surfaceVariantLightMediumContrast
import com.adyen.android.assignment.ui.theme.tertiaryContainerDark
import com.adyen.android.assignment.ui.theme.tertiaryContainerDarkHighContrast
import com.adyen.android.assignment.ui.theme.tertiaryContainerDarkMediumContrast
import com.adyen.android.assignment.ui.theme.tertiaryContainerLight
import com.adyen.android.assignment.ui.theme.tertiaryContainerLightHighContrast
import com.adyen.android.assignment.ui.theme.tertiaryContainerLightMediumContrast
import com.adyen.android.assignment.ui.theme.tertiaryDark
import com.adyen.android.assignment.ui.theme.tertiaryDarkHighContrast
import com.adyen.android.assignment.ui.theme.tertiaryDarkMediumContrast
import com.adyen.android.assignment.ui.theme.tertiaryLight
import com.adyen.android.assignment.ui.theme.tertiaryLightHighContrast
import com.adyen.android.assignment.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
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

private val darkScheme = darkColorScheme(
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

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography(),
    content = content
  )
}

