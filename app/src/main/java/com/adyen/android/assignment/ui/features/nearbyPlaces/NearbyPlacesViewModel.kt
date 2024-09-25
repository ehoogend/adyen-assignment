package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.repository.LocationRepository
import com.adyen.android.assignment.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NearbyPlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val refreshWithLocationPermissionTrigger = MutableSharedFlow<Boolean>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val uiState: StateFlow<PlacesUIState> =
        refreshWithLocationPermissionTrigger
            .transformLatest { hasLocationPermission ->
                if (hasLocationPermission) {
                    emitAll(
                        locationRepository.locationFlow.map {
                            placesRepository.getRecommendedPlaces(it)
                        }
                    )
                } else {
                    emit(null)
                }
            }.map { places ->
                if (places == null) {
                    PlacesUIState.NoPermission
                } else if (places.isEmpty()) {
                    PlacesUIState.Empty
                } else {
                    PlacesUIState.Success(places)
                }
            }.catch {
                Timber.e(it)
                emit(PlacesUIState.Error(it))
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlacesUIState.Loading)

    fun refresh(withLocationPermission: Boolean) {
        refreshWithLocationPermissionTrigger.tryEmit(withLocationPermission)
    }
}

sealed class PlacesUIState {
    data class Success(val places: List<Place>) : PlacesUIState()
    data object Empty : PlacesUIState()
    data object NoPermission : PlacesUIState()
    data class Error(val exception: Throwable) : PlacesUIState()
    data object Loading : PlacesUIState()
}
