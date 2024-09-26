package com.adyen.android.assignment.data.repository

import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) {

    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    val locationFlow = callbackFlow {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                trySend(locationResult.lastLocation)
            }
        }
        try {
            fusedLocationClient.lastLocation.await<Location?>()?.let { send(it) }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                .await()
        } catch (e: SecurityException) {
            close(e)
        }
        awaitClose { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }

    companion object {
        val locationRequest =
            LocationRequest.Builder(10000)
                .setMinUpdateDistanceMeters(100F)
                .build()
    }
}