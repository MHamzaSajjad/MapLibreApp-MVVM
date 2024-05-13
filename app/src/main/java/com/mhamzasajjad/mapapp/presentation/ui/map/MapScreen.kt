package com.mhamzasajjad.mapapp.presentation.ui.map

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mhamzasajjad.mapapp.presentation.theme.appPrimaryColorLightTheme
import com.mhamzasajjad.mapapp.presentation.ui.permissions.PermissionAlertDialog
import com.mhamzasajjad.mapapp.presentation.ui.permissions.RequiredPermission
import com.mhamzasajjad.mapapp.presentation.ui.permissions.getRequiredPermission
import org.ramani.compose.CameraPosition
import org.ramani.compose.LocationRequestProperties
import org.ramani.compose.LocationStyling
import org.ramani.compose.MapLibre

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val userCurrentLocation by viewModel.userCurrentLocation.collectAsState()
    val mapStyleUrl by viewModel.mapStyleUrl.collectAsState()
    val toastText by viewModel.toastText.collectAsState()
    val showToast by viewModel.showToast.collectAsState()
    val refreshMap by viewModel.refreshMap.collectAsState()

    val context = LocalContext.current
    val activity = context as Activity

    val permissionDialog = remember {
        mutableStateListOf<RequiredPermission>()
    }

    val locationsPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissions.entries.forEach { entry ->
                if (!entry.value) {
                    getRequiredPermission(entry.key)?.let { requiredPermission ->
                        permissionDialog.add(requiredPermission)
                    }
                }
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (refreshMap) {
            Card(modifier = Modifier.fillMaxSize(), backgroundColor = Color.White) {}
            viewModel.setRefreshMap(false)
        } else {
            MapLibre(
                modifier = Modifier.fillMaxSize(),
                styleUrl = mapStyleUrl,
                cameraPosition = CameraPosition(
                    target = LatLng(
                        latitude = userCurrentLocation.latitude,
                        longitude = userCurrentLocation.longitude,
                        altitude = userCurrentLocation.altitude
                    ),
                    zoom = 5.0
                ),
                locationRequestProperties = LocationRequestProperties(),
                locationStyling = LocationStyling(
                    enablePulse = true,
                    pulseColor = appPrimaryColorLightTheme.hashCode()
                )
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(30.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = appPrimaryColorLightTheme,
            onClick = {
                if (viewModel.isLocationPermissionAlreadyGranted(context)) {
                    viewModel.onUserLocationButtonClicked()
                } else {
                    locationsPermissionsLauncher.launch(
                        arrayOf(
                            RequiredPermission.COARSE_LOCATION.permission,
                            RequiredPermission.FINE_LOCATION.permission
                        )
                    )
                }
            }) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "User Location"
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(30.dp)
                .align(Alignment.BottomStart),
            backgroundColor = Color.White,
            onClick = {
                viewModel.setMapStyleUrl(
                    isFirstTime = false
                )
            }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Basemap Selector",
                tint = appPrimaryColorLightTheme
            )
        }

        if (showToast) {
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            viewModel.hideToast()
        }

        // only display dialog for one location type currently

        permissionDialog.forEach { permission ->
            if (permission == RequiredPermission.COARSE_LOCATION) {
                PermissionAlertDialog(
                    requiredPermission = permission,
                    onDismiss = { permissionDialog.remove(permission) },
                    onOkClick = {
                        permissionDialog.remove(permission)
                        locationsPermissionsLauncher.launch(arrayOf(permission.permission))
                    },
                    onGoToAppSettingsClick = {
                        permissionDialog.remove(permission)
                        activity.goToAppSetting()
                    },
                    isPermissionDeclined = !activity.shouldShowRequestPermissionRationale(permission.permission)
                )
            }
        }
    }
}

fun Activity.goToAppSetting() {
    val i = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(i)
}

