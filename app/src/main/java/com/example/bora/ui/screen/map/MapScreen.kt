package com.example.bora.ui.screen.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.location.LocationPuck
import org.maplibre.compose.location.rememberDefaultLocationProvider
import org.maplibre.compose.location.rememberUserLocationState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MapScreen() {
    var hasLocationPermission by remember { mutableStateOf(false) }
    val cameraState = rememberCameraState()
    var centeredOnUser by remember { mutableStateOf(false) }

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

    MaplibreMap(
        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/fiord"),
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        options = MapOptions(
            ornamentOptions = OrnamentOptions.OnlyLogo
        )
    ) {
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
}