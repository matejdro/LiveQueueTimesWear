package com.matejdro.livequeuetimeswear.wear.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import com.matejdro.livequeuetimeswear.common.AppColors


@Composable
fun WearAppTheme(
   content: @Composable () -> Unit
) {
   MaterialTheme(
      colors = colorPalette,
      content = content
   )
}

private val colorPalette = Colors(
   primary = AppColors.primary,
   primaryVariant = AppColors.primaryDark,
   secondary = AppColors.primary,
   secondaryVariant = AppColors.primaryDark,
   error = AppColors.error,
   onPrimary = AppColors.onPrimary,
   onSecondary = AppColors.onPrimary,
   onError = AppColors.onError
)
