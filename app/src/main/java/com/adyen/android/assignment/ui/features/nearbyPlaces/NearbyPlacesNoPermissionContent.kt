package com.adyen.android.assignment.ui.features.nearbyPlaces

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.ui.theme.AppTheme
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState

@Composable
fun NearbyPlacesNoPermissionContent(
    locationPermissionsState: MultiplePermissionsState,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val permissionRequestContent =
            if (locationPermissionsState.shouldShowRationale) {
                PermissionRequestContent.ShowRationale
            } else {
                LaunchedEffect(Unit) {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
                PermissionRequestContent.Settings
            }
        Text(
            text = stringResource(id = R.string.empty_message),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search_icon),
        )
        Text(
            text = stringResource(permissionRequestContent.messageText),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = {
                when (permissionRequestContent) {
                    PermissionRequestContent.ShowRationale -> {
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }

                    PermissionRequestContent.Settings -> {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }
                }
            },
            content = {
                Text(text = stringResource(id = permissionRequestContent.buttonText))
            }
        )
    }
}

@Preview
@Composable
private fun NearbyPlacesNoPermissionRationalePreview() {
    AppTheme {
        Scaffold {
            NearbyPlacesNoPermissionContent(
                modifier = Modifier.padding(it),
                locationPermissionsState = object : MultiplePermissionsState {
                    override val allPermissionsGranted: Boolean = false
                    override val permissions: List<PermissionState> = emptyList()
                    override val revokedPermissions: List<PermissionState> = emptyList()
                    override val shouldShowRationale: Boolean = false
                    override fun launchMultiplePermissionRequest() {}
                }
            )
        }
    }
}

@Preview
@Composable
private fun NearbyPlacesNoPermissionSettingsPreview() {
    AppTheme {
        Scaffold {
            NearbyPlacesNoPermissionContent(
                modifier = Modifier.padding(it),
                locationPermissionsState = object : MultiplePermissionsState {
                    override val allPermissionsGranted: Boolean = false
                    override val permissions: List<PermissionState> = emptyList()
                    override val revokedPermissions: List<PermissionState> = emptyList()
                    override val shouldShowRationale: Boolean = false
                    override fun launchMultiplePermissionRequest() {}
                }
            )
        }
    }
}

private enum class PermissionRequestContent(
    @StringRes val messageText: Int,
    @StringRes val buttonText: Int
) {
    ShowRationale(R.string.request_location_text, R.string.request_location_button),
    Settings(R.string.settings_permission_text, R.string.settings_permission_button),
}