package com.matejdro.livequeuetimeswear.data

import com.matejdro.livequeuetimeswear.wear.model.backend.QueueTimes
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class QueueTimesRepository {
   private val service: QueueTimesService

   init {
      val moshi = Moshi.Builder()
         .add(DateAdapter)
         .build()

      val okHttpClient = OkHttpClient.Builder()
         .addInterceptor {
            val newRequest = it.request()
               .newBuilder()
               .header("User-Agent", "https://github.com/matejdro/LiveQueueTimesWear")
               .build()

            it.proceed(newRequest)
         }
         .build()

      service = Retrofit.Builder()
         .baseUrl("https://queue-times.com/")
         .client(okHttpClient)
         .addConverterFactory(MoshiConverterFactory.create(moshi))
         .build()
         .create(QueueTimesService::class.java)
   }

   suspend fun fetchQueueTimes(): QueueTimes {
      return service.getQueueTimes()
   }
}
