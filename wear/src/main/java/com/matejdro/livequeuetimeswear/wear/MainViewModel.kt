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

                val lands = queueTimes.lands
                val rides = lands.map { land ->
                    land.rides
                        .map { ride ->
                            Ride(ride.name, land.name, if (ride.isOpen != true) 999 else ride.waitTime)
                        }
                }
                    .flatten()
                    .filter { WHITELIST_RIDES.any { ride -> it.name.contains(ride) } }
                    .sortedWith(
                        compareByDescending<Ride> { PRIORITY_RIDES.any { ride -> it.name.contains(ride) } }
                            .then(compareBy { it.waitingTimeMinutes })
                    )

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

private val WHITELIST_RIDES = listOf(
    "Das verrückte Hotel Tartüff",
    "Maus au Chocolat",
    "Wellenflug",
    "Feng Ju Palace",
    "Geister Rikscha",
    "Deep in Africa",
    "Crazy Bats",
    "Avoras",
    "Wakobato",
    "Wirtl‘s Taubenturm",
    "Chiapas",
    "Colorado Adventure",
    "Tikal",
    "Mystery Castle",
    "Raik",
    "River Quest",
    "Black Mamba",
    "Winja‘s Fear",
    "Winja‘s Force",
    "Talocan",
    "Taron",
    "F.L.Y",
)

private val PRIORITY_RIDES = listOf(
    "Black Mamba",
    "Winja‘s Fear",
    "Winja‘s Force",
    "Talocan",
    "Taron",
    "F.L.Y",
)
