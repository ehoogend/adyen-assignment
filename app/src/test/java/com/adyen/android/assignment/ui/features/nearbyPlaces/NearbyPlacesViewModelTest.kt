package com.adyen.android.assignment.ui.features.nearbyPlaces

import android.location.Location
import app.cash.turbine.test
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.location.LocationRepository
import com.adyen.android.assignment.data.places.PlacesRepository
import com.adyen.android.assignment.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class NearbyPlacesViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var placesRepository: PlacesRepository

    @RelaxedMockK
    private lateinit var locationRepository: LocationRepository

    private lateinit var viewModel: NearbyPlacesViewModel

    private val mockLocation: Location = mockk(relaxed = true)

    private val locationFlow: MutableStateFlow<Location?> = MutableStateFlow(mockLocation)

    private val mockPlaces: List<Place> = listOf(mockk(relaxed = true), mockk(relaxed = true))

    @Before
    fun setup() {
        every { locationRepository.locationFlow } returns locationFlow
        coEvery { placesRepository.getRecommendedPlaces(mockLocation) } returns mockPlaces
        viewModel = NearbyPlacesViewModel(placesRepository, locationRepository)
    }

    @Test
    fun `uiState emits loading then success after location permission is set`() = runTest {
        viewModel.uiState.test {
            assertEquals(PlacesUIState.Loading(), awaitItem()) // Initial loading state
            viewModel.setLocationPermission(true)
            assertEquals(PlacesUIState.Success(mockPlaces.toImmutableList()), awaitItem())
        }
    }

    @Test
    fun `uiState re-emits success after refresh`() = runTest {
        viewModel.setLocationPermission(true)
        coEvery { placesRepository.getRecommendedPlaces(mockLocation) } returns emptyList()
        viewModel.uiState.test {
            assertEquals(PlacesUIState.Empty, awaitItem())
            coEvery { placesRepository.getRecommendedPlaces(mockLocation) } returns mockPlaces
            viewModel.refresh()
            assertEquals(PlacesUIState.Success(mockPlaces.toImmutableList()), awaitItem())
        }
    }

    @Test
    fun `Collecting uiState without permission emits NoPermission`() = runTest {
        viewModel.setLocationPermission(false)
        viewModel.uiState.test {
            assertEquals(PlacesUIState.NoPermission, awaitItem())
        }
    }

    @Test
    fun `uiState emits success after location permission is granted`() = runTest {
        viewModel.setLocationPermission(false)
        viewModel.uiState.test {
            assertEquals(PlacesUIState.NoPermission, awaitItem())
            viewModel.setLocationPermission(true)
            assertEquals(PlacesUIState.Success(mockPlaces.toImmutableList()), awaitItem())
        }
    }

    @Test
    fun `uiState emits loading after location permission is granted but no location is available yet`() = runTest {
        locationFlow.value = null
        viewModel.setLocationPermission(false)
        viewModel.uiState.test {
            assertEquals(PlacesUIState.NoPermission, awaitItem())
            viewModel.setLocationPermission(true)
            assertEquals(PlacesUIState.Loading(true), awaitItem())
        }
    }

    @Test
    fun `uiState emits success after location becomes available`() = runTest {
        locationFlow.value = null
        viewModel.setLocationPermission(true)
        viewModel.uiState.test {
            assertEquals(PlacesUIState.Loading(true), awaitItem())
            locationFlow.value = mockLocation
            assertEquals(PlacesUIState.Success(mockPlaces.toImmutableList()), awaitItem())
        }
    }

    @Test
    fun `PlacesUIState Error is emitted when placesRepository throws an exception`() = runTest {
        viewModel.setLocationPermission(true)
        val exception = Exception("Test Exception")
        coEvery { placesRepository.getRecommendedPlaces(mockLocation) } throws exception
        viewModel.uiState.test {
            assertIs<PlacesUIState.Error>(awaitItem())
        }
    }

    @Test
    fun `PlacesUIState Error is resolved after refreshing successfully`() = runTest {
        viewModel.setLocationPermission(true)
        val exception = Exception("Test Exception")
        coEvery { placesRepository.getRecommendedPlaces(mockLocation) } throws exception
        viewModel.uiState.test {
            assertIs<PlacesUIState.Error>(awaitItem())
            coEvery { placesRepository.getRecommendedPlaces(mockLocation) } returns mockPlaces
            viewModel.refresh()
            assertEquals(PlacesUIState.Success(mockPlaces.toImmutableList()), awaitItem())
        }
    }
}
