package com.adyen.android.assignment.ui.features.nearbyPlaces.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

private const val metersInKm = 1000.0

@Composable
fun PlaceInfoRow(place: Place, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        DistanceInfo(
            distance = place.distance,
        )
        place.rating?.let {
            Icon(
                modifier = Modifier
                    .size(4.dp)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Default.Circle,
                contentDescription = Icons.Default.Circle.name
            )
            RatingInfo(
                rating = place.rating,
            )
        }
    }
}

@Composable
private fun DistanceInfo(distance: Int, modifier: Modifier = Modifier) {
    val kilometers = remember(distance) { distance / metersInKm }
    InfoComponent(
        icon = Icons.Default.LocationOn,
        text = stringResource(R.string.kms_format, kilometers),
        modifier = modifier
    )
}

@Composable
private fun RatingInfo(rating: Float, modifier: Modifier = Modifier) {
    InfoComponent(
        icon = Icons.Default.Star,
        text = stringResource(R.string.rating_format, rating),
        modifier = modifier
    )
}

@Composable
private fun InfoComponent(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = icon.name,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun PlaceInfoRowPreview() {
    AppTheme {
        PlaceInfoRow(place = Place.preview)
    }
}