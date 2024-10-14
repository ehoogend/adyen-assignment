package com.adyen.android.assignment.data.repository

import android.location.Location
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.places.PlacesRepository
import com.adyen.android.assignment.data.places.remote.RemotePlacesDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

class PlacesRepositoryTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var placesRemoteDataSource: RemotePlacesDataSource

    private lateinit var placesRepository: PlacesRepository

    @RelaxedMockK
    private lateinit var mockLocation: Location

    @Before
    fun setup() {
        placesRepository = PlacesRepository(placesRemoteDataSource)
    }

    @Test
    fun `getRecommendedPlaces Success`() = runTest {
        val returnedList = listOf(mockk<Place>())
        coEvery { placesRemoteDataSource.getRecommendedPlaces(mockLocation) } returns returnedList

        val places = placesRepository.getRecommendedPlaces(mockLocation)
        // Verify that the function successfully retrieves a list of places for a valid location.
        assertEquals(returnedList, places)
        coVerify { placesRemoteDataSource.getRecommendedPlaces(mockLocation) }
    }

    @Test(expected = IOException::class)
    fun `getRecommendedPlaces propagates exceptions`() = runTest {
        coEvery { placesRemoteDataSource.getRecommendedPlaces(mockLocation) } throws IOException()

        // Throws
        placesRepository.getRecommendedPlaces(mockLocation)
    }
}
