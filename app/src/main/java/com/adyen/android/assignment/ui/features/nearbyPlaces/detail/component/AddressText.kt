package com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adyen.android.assignment.api.places.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun AddressText(place: Place, modifier: Modifier = Modifier) {
    val addressText =
        rememberSaveable(place) {
            listOfNotNull(
                place.location.address,
                place.location.locality,
                place.location.region,
                place.location.postcode,
                place.location.country,
            ).joinToString(", ")
        }
    Text(
        modifier = modifier,
        text = addressText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview
@Composable
private fun AddressTextPreview() {
    AppTheme {
        Surface {
            AddressText(place = Place.preview)
        }
    }
}
