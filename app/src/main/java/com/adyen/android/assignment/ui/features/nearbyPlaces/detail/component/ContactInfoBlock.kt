package com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.data.places.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun ContactInfoBlock(place: Place, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        place.tel?.let {
            ContactInfoElement(
                text = it,
                icon = Icons.Default.Call,
                intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$it)")
                }
            )
        }
        place.website?.let {
            ContactInfoElement(
                text = it,
                icon = Icons.Default.Web,
                intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(it)
                }
            )
        }
        place.email?.let {
            ContactInfoElement(
                text = it,
                icon = Icons.Default.Web,
                intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$it")
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.ContactInfoElement(
    text: String,
    icon: ImageVector,
    intent: Intent,
    context: Context = LocalContext.current
) {
    Spacer(modifier = Modifier.padding(4.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = { context.startActivity(intent) }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun ContactInfoBlockPreview() {
    AppTheme {
        Surface {
            ContactInfoBlock(
                place = Place.preview
            )
        }
    }
}
