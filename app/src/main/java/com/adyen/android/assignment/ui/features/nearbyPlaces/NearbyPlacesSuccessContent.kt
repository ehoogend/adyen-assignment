package com.adyen.android.assignment.ui.features.nearbyPlaces

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlacesSuccessContent(places: List<Place>, onClickPlace: (Place) -> Unit) {
    val configuration = LocalConfiguration.current
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 2
                else -> 1
            }
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(places, key = { place -> place.fsqId }) { place ->
            NearbyPlaceItem(
                place = place,
                onClick = { onClickPlace(place) }
            )
        }
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