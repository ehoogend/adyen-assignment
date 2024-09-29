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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NearbyPlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    locationRepository: LocationRepository,
) : ViewModel() {

    private val permissionState = MutableStateFlow<Boolean?>(null)

    private val refresh = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply { tryEmit(Unit) }

    private val placesForLocationFlow =
        locationRepository.locationFlow.transformLatest { location ->
            if (location == null) {
                emit(PlacesUIState.Loading(waitingForLocation = true))
            } else {
                emit(PlacesUIState.Loading())
                val places =
                    placesRepository.getRecommendedPlaces(location).sortedBy { it.distance }
                if (places.isEmpty()) {
                    emit(PlacesUIState.Empty)
                } else {
                    emit(PlacesUIState.Success(places.toImmutableList()))
                }
            }
        }.catch {
            Timber.e(it)
            emit(PlacesUIState.Error(it))
        }

    val uiState: StateFlow<PlacesUIState> = combine(
        permissionState.filterNotNull(),
        refresh
    ) { hasLocationPermission, _ ->
        hasLocationPermission
    }.transformLatest { hasLocationPermission ->
        if (hasLocationPermission) {
            emitAll(placesForLocationFlow)
        } else {
            emit(PlacesUIState.NoPermission)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlacesUIState.Loading())

    fun setLocationPermission(withLocationPermission: Boolean) =
        permissionState.tryEmit(withLocationPermission)

    fun refresh() {
        refresh.tryEmit(Unit)
    }
}

sealed class PlacesUIState {
    data class Success(val places: ImmutableList<Place>) : PlacesUIState()
    data object Empty : PlacesUIState()
    data object NoPermission : PlacesUIState()
    data class Error(val exception: Throwable) : PlacesUIState()
    data class Loading(val waitingForLocation: Boolean = false) : PlacesUIState()
}
