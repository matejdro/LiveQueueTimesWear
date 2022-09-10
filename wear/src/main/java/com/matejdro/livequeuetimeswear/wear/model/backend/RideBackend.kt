package com.matejdro.livequeuetimeswear.wear.model.backend


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class RideBackend(
    val id: Int?,
    @Json(name = "is_open")
    val isOpen: Boolean?,
    @Json(name = "last_updated")
    val lastUpdated: Instant?,
    val name: String,
    @Json(name = "wait_time")
    val waitTime: Int
)
