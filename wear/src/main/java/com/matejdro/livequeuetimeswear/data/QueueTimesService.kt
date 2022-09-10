package com.matejdro.livequeuetimeswear.data

import com.matejdro.livequeuetimeswear.wear.model.backend.QueueTimes
import retrofit2.http.GET

interface QueueTimesService {
   @GET("parks/12/queue_times.json")
   suspend fun getQueueTimes(): QueueTimes
}
