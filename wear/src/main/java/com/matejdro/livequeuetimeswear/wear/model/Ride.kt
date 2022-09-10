package com.matejdro.livequeuetimeswear.wear.model

import androidx.compose.runtime.Immutable

@Immutable
data class Ride(
   val name: String,
   val waitingTimeMinutes: Int
)
