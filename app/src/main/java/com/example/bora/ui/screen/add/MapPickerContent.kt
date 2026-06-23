package com.example.bora.ui.screen.add

import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MapPickerContent(
    initialLat: Double?,
    initialLng: Double?,
    onConfirm: (address: String, lat: Double, lng: Double) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedPosition by remember { mutableStateOf<Position?>(null) }
    val cameraState = rememberCameraState()

    LaunchedEffect(initialLat, initialLng) {
        if (initialLat != null && initialLng != null && selectedPosition == null) {
            selectedPosition = Position(initialLng, initialLat)
        }
    }

    LaunchedEffect(selectedPosition) {
        selectedPosition?.let {
            cameraState.animateTo(
                CameraPosition(target = it, zoom = 15.0),
                duration = 300.milliseconds
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MaplibreMap(
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/fiord"),
            modifier = Modifier.fillMaxSize(),
            cameraState = cameraState,
            options = MapOptions(ornamentOptions = OrnamentOptions.OnlyLogo),
            onMapClick = { position, _ ->
                selectedPosition = position
                ClickResult.Consume
            }
        ) {
            val markerSource = rememberGeoJsonSource(
                data = selectedPosition?.let { pos ->
                    val point = Point(coordinates = pos)
                    val feature = Feature<Point, Any?>(
                        geometry = point,
                        properties = null
                    )
                    val collection = FeatureCollection(
                        features = listOf(feature)
                    )
                    GeoJsonData.Features(collection)
                } ?: GeoJsonData.Features(FeatureCollection<Point, Any?>(emptyList()))
            )

            if (selectedPosition != null) {
                CircleLayer(
                    id = "selected-marker",
                    source = markerSource,
                    color = const(Color.Red),
                    radius = const(10.dp),
                    strokeColor = const(Color.White),
                    strokeWidth = const(2.dp)
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
                Text(
                    text = "Selecionar local",
                    fontWeight = FontWeight.Medium
                )
                TextButton(
                    onClick = {
                        selectedPosition?.let { pos ->
                            val geocoder = Geocoder(context, Locale.getDefault())
                            val addresses = geocoder.getFromLocation(
                                pos.latitude,
                                pos.longitude,
                                1
                            )
                            val address = addresses?.firstOrNull()?.getAddressLine(0)
                                ?: "${pos.latitude}, ${pos.longitude}"
                            onConfirm(address, pos.latitude, pos.longitude)
                        }
                    },
                    enabled = selectedPosition != null
                ) {
                    Text("Confirmar")
                }
            }
        }
    }
}
