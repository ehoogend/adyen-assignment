package com.adyen.android.assignment.ui.features.nearbyPlaces

import android.Manifest
import android.content.Context
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun NearbyPlacesRoute(
    onClickPlace: (Place) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: NearbyPlacesViewModel = hiltViewModel(),
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    val hasLocationPermission = rememberSaveable {
        locationPermissionsState.permissions.any { it.status.isGranted }
    }

    NearbyPlacesScreen(
        placesUIState = viewModel.uiState.collectAsState().value,
        onClickRetry = viewModel::refresh,
        onClickPlace = onClickPlace,
        locationPermissionsState = locationPermissionsState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
    )
    // Ensure ViewModel is always updated with the latest value of location permission state.
    LaunchedEffect(locationPermissionsState) {
        viewModel.setLocationPermission(hasLocationPermission)
    }
}

@Composable
private fun NearbyPlacesScreen(
    placesUIState: PlacesUIState,
    onClickRetry: () -> Unit,
    onClickPlace: (Place) -> Unit,
    locationPermissionsState: MultiplePermissionsState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val snackbarHostState = remember { SnackbarHostState() }
    FineLocationPermissionSnackbarContent(
        locationPermissionsState = locationPermissionsState,
        snackbarHostState = snackbarHostState,
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column {
                when (placesUIState) {
                    is PlacesUIState.Loading -> NearbyPlacesLoadingContent(placesUIState.waitingForLocation)
                    PlacesUIState.Empty -> NearbyPlacesEmptyContent(onClickRetry = onClickRetry)
                    PlacesUIState.NoPermission -> NearbyPlacesNoPermissionContent(
                        locationPermissionsState = locationPermissionsState
                    )

                    is PlacesUIState.Success -> NearbyPlacesSuccessContent(
                        placesUIState.places,
                        onClickPlace = onClickPlace,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                    )

                    is PlacesUIState.Error -> NearbyPlacesErrorContent(onClickRetry = onClickRetry)
                }
            }
        }
    }
}

@Composable
private fun FineLocationPermissionSnackbarContent(
    locationPermissionsState: MultiplePermissionsState,
    snackbarHostState: SnackbarHostState,
    context: Context = LocalContext.current,
) {
    var fineLocationPermissionSnackbarShown by rememberSaveable(locationPermissionsState) {
        mutableStateOf(false)
    }
    val shouldShowFinePermissionRequestRationale =
        !locationPermissionsState.allPermissionsGranted &&
            locationPermissionsState.permissions.any { it.status.isGranted }

    if (!fineLocationPermissionSnackbarShown && shouldShowFinePermissionRequestRationale) {
        LaunchedEffect(snackbarHostState) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.request_fine_location_text),
                actionLabel = context.getString(R.string.request_location_button),
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )
            when (result) {
                SnackbarResult.Dismissed -> {
                    fineLocationPermissionSnackbarShown = true
                }

                SnackbarResult.ActionPerformed -> {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
            }
        }
    }
}
