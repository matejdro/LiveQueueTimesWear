package com.matejdro.livequeuetimeswear.wear.model.backend


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LandBackend(
    val id: Int?,
    val name: String?,
    val rides: List<RideBackend> = emptyList()
)
