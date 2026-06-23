package com.example.bora.ui.screen.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.location.LocationPuck
import org.maplibre.compose.location.rememberDefaultLocationProvider
import org.maplibre.compose.location.rememberUserLocationState
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
import kotlin.time.Duration.Companion.milliseconds
import com.example.bora.model.Event
import com.example.bora.model.EVENT_DATE_FORMATTER

@Composable
fun MapScreen(
    state: MapUiState,
    onClickEvent: (String) -> Unit
) {
    var hasLocationPermission by remember { mutableStateOf(false) }
    val cameraState = rememberCameraState()
    var centeredOnUser by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    val featureCollection = remember(state.events) {
        val features = state.events.map { event ->
            Feature(
                geometry = Point(coordinates = Position(event.lng, event.lat)),
                properties = buildJsonObject {
                    put("id", event.id)
                    put("name", event.name)
                }
            )
        }
        FeatureCollection(features = features)
    }

    MaplibreMap(
        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/fiord"),
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        options = MapOptions(
            ornamentOptions = OrnamentOptions.OnlyLogo
        )
    ) {
        val eventsSource = rememberGeoJsonSource(
            data = GeoJsonData.Features(featureCollection)
        )

        CircleLayer(
            id = "event-markers",
            source = eventsSource,
            color = const(Color.Red),
            radius = const(10.dp),
            strokeColor = const(Color.White),
            strokeWidth = const(2.dp),
            onClick = { features ->
                features.firstOrNull()?.let { f ->
                    val id = f.properties?.get("id")?.jsonPrimitive?.content
                    id?.let { eventId ->
                        selectedEvent = state.events.find { it.id == eventId }
                    }
                }
                ClickResult.Consume
            }
        )

        if (hasLocationPermission) {
            val locationProvider = rememberDefaultLocationProvider()
            val locationState = rememberUserLocationState(locationProvider)

            LaunchedEffect(locationState.location) {
                val loc = locationState.location
                if (loc != null && !centeredOnUser) {
                    cameraState.animateTo(
                        finalPosition = CameraPosition(target = loc.position.value, zoom = 15.0),
                        duration = 1000.milliseconds,
                    )
                    centeredOnUser = true
                }
            }

            LocationPuck(
                idPrefix = "User",
                location = locationState.location,
                cameraState = cameraState,
                showBearing = false,
            )
        }
    }

    selectedEvent?.let { event ->
        AlertDialog(
            onDismissRequest = { selectedEvent = null },
            title = { Text(event.name) },
            text = {
                Column {
                    Text(
                        text = event.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = event.date.format(EVENT_DATE_FORMATTER),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onClickEvent(event.id)
                    selectedEvent = null
                }) {
                    Text("Detalhes")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedEvent = null }) {
                    Text("Fechar")
                }
            }
        )
    }
}
