package com.matejdro.livequeuetimeswear.wear.model.backend


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QueueTimes(
   val lands: List<LandBackend> = emptyList()
)
