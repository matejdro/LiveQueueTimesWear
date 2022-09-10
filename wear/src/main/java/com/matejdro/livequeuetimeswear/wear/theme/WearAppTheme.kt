package com.matejdro.livequeuetimeswear.wear.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme


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
   primary = Color(0xfffca905),
   primaryVariant = Color(0xffc37a00),
   secondary = Color(0xfffca905),
   secondaryVariant = Color(0xffc37a00),
   error = Color(0xffef5350),
   onPrimary = Color.Black,
   onSecondary = Color.Black,
   onError = Color.Black
)
