package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.repository.LocationRepository
import com.adyen.android.assignment.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NearbyPlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val permissionState = MutableSharedFlow<Boolean>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val refresh = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply { tryEmit(Unit) }

    val uiState: StateFlow<PlacesUIState> = combineTransform(
        permissionState.distinctUntilChanged(),
        refresh
    ) { hasLocationPermission, _ ->
        emit(PlacesUIState.Loading())
        if (hasLocationPermission) {
            emitAll(locationRepository.locationFlow.map(::getPlacesForLocation))
        } else {
            emit(PlacesUIState.NoPermission)
        }
    }.catch {
        Timber.e(it)
        emit(PlacesUIState.Error(it))
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlacesUIState.Loading())

    private suspend fun getPlacesForLocation(location: android.location.Location?): PlacesUIState {
        return if (location == null) {
            PlacesUIState.Loading(waitingForLocation = true)
        } else {
            val places =
                placesRepository.getRecommendedPlaces(location).sortedBy { it.distance }
            if (places.isEmpty()) {
                PlacesUIState.Empty
            } else {
                PlacesUIState.Success(places.toImmutableList())
            }
        }
    }

    fun setLocationPermission(withLocationPermission: Boolean) = permissionState.tryEmit(withLocationPermission)

    fun refresh() = refresh.tryEmit(Unit)
}

sealed class PlacesUIState {
    data class Success(val places: ImmutableList<Place>) : PlacesUIState()
    data object Empty : PlacesUIState()
    data object NoPermission : PlacesUIState()
    data class Error(val exception: Throwable) : PlacesUIState()
    data class Loading(val waitingForLocation: Boolean = false) : PlacesUIState()
}
