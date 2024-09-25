package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlacesLoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun NearbyPlacesLoadingPreview() {
    AppTheme {
        Scaffold {
            NearbyPlacesLoadingContent(
                modifier = Modifier.padding(it)
            )
        }
    }
}
