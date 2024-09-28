package com.adyen.android.assignment.data.places.remote

import android.location.Location
import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.api.model.PlacesResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class RemotePlacesDataSourceTest {
    private val placesService = mockk<PlacesService>()
    private val remotePlacesDataSource = RemotePlacesDataSource(placesService)

    private val mockLocation = mockk<Location>()

    @Before
    fun setUp() {
        every { mockLocation.latitude } returns 52.376510
        every { mockLocation.longitude } returns 4.905890
    }

    @Test
    fun `Successful Recommendation Retrieval`() = runTest {
        val mockPlaces = listOf(mockk<Place>(), mockk<Place>())
        val mockResponse = PlacesResponse(results = mockPlaces)

        coEvery { placesService.getVenueRecommendations(any()) } returns mockResponse

        val result = remotePlacesDataSource.getRecommendedPlaces(mockLocation)

        assertEquals(mockPlaces, result)
    }

    @Test
    fun `Empty Recommendation List`() = runTest {
        val mockResponse = PlacesResponse(results = emptyList())

        coEvery { placesService.getVenueRecommendations(any()) } returns mockResponse

        val result = remotePlacesDataSource.getRecommendedPlaces(mockLocation)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `Null Location Handling`() = runTest {
        val mockResponse = PlacesResponse(results = emptyList())

        coEvery { placesService.getVenueRecommendations(any()) } returns mockResponse

        val result = remotePlacesDataSource.getRecommendedPlaces(null)

        assertEquals(emptyList(), result)
    }

    @Test(expected = Exception::class)
    fun `Network Error Handling`() = runTest {
        coEvery { placesService.getVenueRecommendations(any()) } throws Exception("Network Error")

        remotePlacesDataSource.getRecommendedPlaces(mockLocation)
    }
}