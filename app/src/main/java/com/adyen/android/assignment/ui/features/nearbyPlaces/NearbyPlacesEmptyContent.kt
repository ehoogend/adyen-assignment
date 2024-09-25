package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun NearbyPlacesEmptyContent(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.empty_header)
        )
        Icon(
            modifier = Modifier.size(128.dp),
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

@Preview
@Composable
private fun NearbyPlacesEmptyPreview() {
    AppTheme {
        Scaffold {
            NearbyPlacesEmptyContent(
                modifier = Modifier.padding(it),
                onClickRetry = {}
            )
        }
    }
}