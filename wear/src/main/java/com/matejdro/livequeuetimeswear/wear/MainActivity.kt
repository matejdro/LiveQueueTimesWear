package com.matejdro.livequeuetimeswear.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeTextDefaults
import com.matejdro.livequeuetimeswear.wear.model.Ride
import com.matejdro.livequeuetimeswear.wear.model.RideData
import com.matejdro.livequeuetimeswear.wear.model.Status
import com.matejdro.livequeuetimeswear.wear.theme.WearAppTheme
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
   private val viewModel by viewModels<MainViewModel>()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         WearAppTheme {
            val rideData = viewModel.data.collectAsState().value

            RideList(rideData, viewModel::refresh)
         }
      }
   }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RideList(rideData: RideData, refresh: () -> Unit) {
   val scrollState = rememberScrollState()
   val coroutineScope = rememberCoroutineScope()
   val focusRequester = remember { FocusRequester() }


   Column(
      Modifier
         .fillMaxSize()
         .verticalScroll(scrollState)
         .onRotaryScrollEvent {
            coroutineScope.launch {
               scrollState.scrollTo(
                  (scrollState.value +
                          it.verticalScrollPixels).roundToInt()
               )
            }
            true
         }
         .focusRequester(focusRequester)
         .focusable()
         .padding(vertical = 16.dp, horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      Text(TimeTextDefaults.timeSource(TimeTextDefaults.TimeFormat24Hours).currentTime)

      Button(onClick = refresh, enabled = rideData.status != Status.LOADING) {
         val text = if (rideData.status == Status.LOADING) {
            "Refreshing..."
         } else {
            "Refresh"
         }

         Text(text, Modifier.padding(horizontal = 16.dp))
      }

      if (rideData.lastRefresh != null) {
         val errorSuffix = if (rideData.status == Status.ERROR) {
            "\n(ERR)"
         } else {
            ""
         }
         val zdt = rideData.lastRefresh.atZone(ZoneId.systemDefault())
         Text(
            "Last refreshed on ${DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(zdt)}$errorSuffix",
            textAlign = TextAlign.Center
         )
      }

      for (ride in rideData.rides) {
         Text(
            "${ride.name} - ${ride.waitingTimeMinutes} min",
            textAlign = TextAlign.Center
         )
      }
   }

   LaunchedEffect(Unit) {
      focusRequester.requestFocus()
   }
}

@Composable
@Preview(showBackground = true, device = Devices.WEAR_OS_LARGE_ROUND)
private fun RideListPreview() {
   WearAppTheme {
      RideList(
         RideData(
            Status.SUCCESS,
            ZonedDateTime.of(2022, 3, 10, 10, 30, 0, 0, ZoneId.systemDefault()).toInstant(),
            listOf(
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2)
            )
         )
      ) {}
   }
}

@Composable
@Preview(showBackground = true, device = Devices.WEAR_OS_LARGE_ROUND)
private fun RideListRefreshing() {
   WearAppTheme {
      RideList(
         RideData(
            Status.LOADING,
            ZonedDateTime.of(2022, 3, 10, 10, 30, 0, 0, ZoneId.systemDefault()).toInstant(),
            listOf(
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2)
            )
         )
      ) {}
   }
}

@Composable
@Preview(showBackground = true, device = Devices.WEAR_OS_LARGE_ROUND)
private fun RideListError() {
   WearAppTheme {
      RideList(
         RideData(
            Status.ERROR,
            ZonedDateTime.of(2022, 3, 10, 10, 30, 0, 0, ZoneId.systemDefault()).toInstant(),
            listOf(
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2),
               Ride("Super Coaster", 7),
               Ride("Splasher", 2),
               Ride("House on the Hilld", 2)
            )
         )
      ) {}
   }
}
