package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

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
private fun NearbyPlacesScreen(
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

@Composable
private fun NearbyPlacesSuccessContent(places: List<Place>, onClickPlace: (Place) -> Unit) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp
    ) {
        items(places) { place ->
            NearbyPlaceItem(place) {
                onClickPlace(place)
            }
        }
    }
}

@Composable
private fun NearbyPlaceItem(place: Place, onClick: () -> Unit) {
    Card(
        onClick = onClick
    ) {
        Text(text = place.name)
        Text(text = place.location.address ?: "")
        Text(text = stringResource(R.string.distance_format, place.distance))
    }
}

@Composable
private fun NearbyPlacesErrorContent(
    onClickRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.error_header)
        )
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Default.Build,
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = stringResource(R.string.warning_icon)
        )
        Text(text = stringResource(id = R.string.error_message))
        Button(
            onClick = onClickRetry,
            content = {
                Text(text = stringResource(id = R.string.retry))
            }
        )
    }
}

@Composable
private fun NearbyPlacesEmptyContent(onClickRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.empty_header)
        )
        Image(
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Fit,
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.warning_icon)
        )
        Text(text = stringResource(id = R.string.empty_message))
        Button(
            onClick = onClickRetry,
            content = {
                Text(text = stringResource(id = R.string.search_again))
            }
        )
    }
}

@Composable
private fun NearbyPlacesLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun NearbyPlacesSuccessPreview() {
    AppTheme {
        NearbyPlacesScreen(
            placesUIState = PlacesUIState.Success(listOf()),
            onClickRetry = {},
            onClickPlace = {})
    }
}


@Preview
@Composable
private fun NearbyPlacesLoadingPreview() {
    AppTheme {
        NearbyPlacesScreen(
            placesUIState = PlacesUIState.Loading,
            onClickRetry = {},
            onClickPlace = {}
        )
    }
}

@Preview
@Composable
private fun NearbyPlacesErrorPreview() {
    AppTheme {
        NearbyPlacesScreen(
            placesUIState = PlacesUIState.Error(IllegalStateException()),
            onClickRetry = {},
            onClickPlace = {})
    }
}

@Preview
@Composable
private fun NearbyPlacesEmptyPreview() {
    AppTheme {
        NearbyPlacesScreen(
            placesUIState = PlacesUIState.Empty,
            onClickRetry = {},
            onClickPlace = {})
    }
}

