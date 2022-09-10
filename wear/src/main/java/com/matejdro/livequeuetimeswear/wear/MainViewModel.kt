package com.matejdro.livequeuetimeswear.wear

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matejdro.livequeuetimeswear.data.QueueTimesRepository
import com.matejdro.livequeuetimeswear.wear.model.Ride
import com.matejdro.livequeuetimeswear.wear.model.RideData
import com.matejdro.livequeuetimeswear.wear.model.Status
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
   private val repo = QueueTimesRepository()

   private val _data = MutableStateFlow<RideData>(RideData(Status.LOADING, null, emptyList()))
   val data: MutableStateFlow<RideData>
      get() = _data

   private var lastJob: Job? = null

   init {
      refresh()
   }

   fun refresh() {
      lastJob?.cancel()

      lastJob = viewModelScope.launch {
         try {
            _data.update {
               it.copy(status = Status.LOADING)
            }
            val queueTimes = repo.fetchQueueTimes()

            val lands = queueTimes.lands.filterNot { it.name == "Fantasy" }
            val rides = lands.map { land ->
               land.rides.filter { it.isOpen != false }.map { ride ->
                  Ride(ride.name, ride.waitTime)
               }
            }.flatten()
               .sortedBy { it.waitingTimeMinutes }

            val lastRefreshTime = lands.maxOfWithOrNull(nullsFirst()) { land ->
               land.rides.maxOfWithOrNull(nullsFirst()) {
                  it.lastUpdated
               }
            }

            _data.update {
               it.copy(status = Status.SUCCESS, lastRefreshTime, rides)
            }
         } catch (e: Exception) {
            e.printStackTrace()
            _data.update { it.copy(status = Status.ERROR) }
         }
      }
   }
}
