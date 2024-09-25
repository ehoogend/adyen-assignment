package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.api.model.Place

@Composable
fun NearbyPlacesRoute(
    viewModel: NearbyPlacesViewModel = hiltViewModel(),
    onClickPlace: (Place) -> Unit
) {
    NearbyPlacesScreen(
        viewModel.uiState.collectAsState().value,
        viewModel::refresh,
        onClickPlace
    )
}

@Composable
fun NearbyPlacesScreen(
    placesUIState: PlacesUIState,
    onClickRetry: () -> Unit,
    onClickPlace: (Place) -> Unit,
) {
    Scaffold { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (placesUIState) {
                PlacesUIState.Loading -> NearbyPlacesLoadingContent()
                PlacesUIState.Empty -> NearbyPlacesEmptyContent(onClickRetry)
                is PlacesUIState.Success -> NearbyPlacesSuccessContent(
                    placesUIState.places,
                    onClickPlace = onClickPlace
                )

                is PlacesUIState.Error -> NearbyPlacesErrorContent(onClickRetry)
            }
        }
    }
}
