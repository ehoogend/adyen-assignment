package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlacesLoadingContent(waitingForLocation: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    if (waitingForLocation) {
                        R.string.checking_where_you_are
                    } else {
                        R.string.finding_places_around
                    }
                )
            )
        }
    }
}

@Preview
@Composable
internal fun NearbyPlacesLoadingPreview() {
    AppTheme {
        Scaffold {
            NearbyPlacesLoadingContent(
                waitingForLocation = true,
                modifier = Modifier.padding(it)
            )
        }
    }
}
