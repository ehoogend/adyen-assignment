package com.adyen.android.assignment.ui.features.nearbyPlaces

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlacesSuccessContent(
    places: List<Place>,
    onClickPlace: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        columns = StaggeredGridCells.Fixed(
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 3
                else -> 2
            }
        ),
        verticalItemSpacing = 12.dp,
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
        Scaffold {
            NearbyPlacesSuccessContent(
                modifier = Modifier.padding(it),
                places = listOf(),
                onClickPlace = {}
            )
        }
    }
}