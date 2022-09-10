package com.matejdro.livequeuetimeswear.wear.model

import androidx.compose.runtime.Immutable
import java.time.Instant

@Immutable
data class RideData(
   val status: Status,
   val lastRefresh: Instant?,
   val rides: List<Ride>
)
