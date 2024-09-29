package com.adyen.android.assignment.data.repository

import android.location.Location
import app.cash.turbine.test
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class LocationRepositoryTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RelaxedMockK
    private lateinit var mockGetTokenTask: Task<Location>

    @RelaxedMockK
    private lateinit var mockLastLocation: Location

    private lateinit var locationRepository: LocationRepository

    private lateinit var locationCallback: LocationCallback

    @Before
    fun setup() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        locationRepository = LocationRepository(fusedLocationClient)
        every { fusedLocationClient.lastLocation } returns mockGetTokenTask
        coEvery { mockGetTokenTask.await() } returns mockLastLocation

        every {
            fusedLocationClient.requestLocationUpdates(any(), any<LocationCallback>(), any())
        } answers {
            locationCallback = secondArg<LocationCallback>()
            Tasks.forResult(mockk())
        }
    }

    @Test
    fun `locationFlow adds callback when flow is opened`() = runTest {
        locationRepository.locationFlow.test {
            assertEquals(mockLastLocation, awaitItem())
        }
        verify { fusedLocationClient.requestLocationUpdates(any(), locationCallback, any()) }
    }


    @Test
    fun `locationFlow removes callback after flow is closed`() = runTest {
        locationRepository.locationFlow.test {
            assertEquals(mockLastLocation, awaitItem())
        }
        verify { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }

    @Test
    fun `locationFlow emits know lastLocation initially`() = runTest {
        locationRepository.locationFlow.test {
            assertEquals(mockLastLocation, awaitItem())
        }
        verify { fusedLocationClient.lastLocation }
    }


    @Test
    fun `locationFlow emits when updates are received`() = runTest {
        val mockLocation1: Location = mockk()
        val mockLocation2: Location = mockk()
        val locationResult1 = LocationResult.create(listOf(mockLocation1))
        val locationResult2 = LocationResult.create(listOf(mockLocation2))

        locationRepository.locationFlow.test {
            assertEquals(mockLastLocation, awaitItem())
            locationCallback.onLocationResult(locationResult1)
            assertEquals(mockLocation1, awaitItem())
            locationCallback.onLocationResult(locationResult2)
            assertEquals(mockLocation2, awaitItem())
        }
        verify { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }

    @Test
    fun `Permission Denied on lastLocation emits error`() = runTest {
        every { fusedLocationClient.lastLocation } throws SecurityException()

        locationRepository.locationFlow.test {
            awaitError()
        }
    }
}
