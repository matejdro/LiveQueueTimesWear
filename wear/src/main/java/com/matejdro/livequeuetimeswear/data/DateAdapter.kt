package com.matejdro.livequeuetimeswear.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant
import java.time.format.DateTimeFormatter

object DateAdapter {
   @FromJson
   fun fromJsonToInstant(input: String?): Instant? {
      return input?.let { Instant.from(DateTimeFormatter.ISO_INSTANT.parse(it)) }
   }

   @ToJson
   fun toJsonFromInstant(input: Instant?): String? {
      return input?.let { DateTimeFormatter.ISO_INSTANT.format(it) }
   }
}
