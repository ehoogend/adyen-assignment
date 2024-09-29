package com.adyen.android.assignment.data.places.remote

import android.location.Location
import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.api.model.PlacesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class RemotePlacesDataSourceTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var placesService: PlacesService

    private lateinit var remotePlacesDataSource: RemotePlacesDataSource

    @RelaxedMockK
    private lateinit var mockLocation: Location

    @Before
    fun setUp() {
        every { mockLocation.latitude } returns 52.37651
        every { mockLocation.longitude } returns 4.90589
        remotePlacesDataSource = RemotePlacesDataSource(placesService)
    }

    @Test
    fun `Successful Recommendation Retrieval uses correctly formatted location`() = runTest {
        val mockPlaces = listOf(mockk<Place>(), mockk<Place>())
        val mockResponse = PlacesResponse(results = mockPlaces)

        coEvery { placesService.getNearbyPlaces(any(), any(), any()) } returns mockResponse

        val result = remotePlacesDataSource.getRecommendedPlaces(mockLocation)

        assertEquals(mockPlaces, result)

        coVerify {
            placesService.getNearbyPlaces(
                fields = any(),
                location = "52.37651,4.90589",
                limit = any()
            )
        }
    }

    @Test
    fun `Empty Recommendation List is propagated`() = runTest {
        val mockResponse = PlacesResponse(results = emptyList())

        coEvery { placesService.getNearbyPlaces(any(), any(), any()) } returns mockResponse

        val result = remotePlacesDataSource.getRecommendedPlaces(mockLocation)

        assertEquals(emptyList(), result)
    }

    @Test(expected = Exception::class)
    fun `Network Errors get propagated`() = runTest {
        coEvery {
            placesService.getNearbyPlaces(
                any(),
                any(),
                any()
            )
        } throws Exception("Network Error")

        remotePlacesDataSource.getRecommendedPlaces(mockLocation)
    }
}
