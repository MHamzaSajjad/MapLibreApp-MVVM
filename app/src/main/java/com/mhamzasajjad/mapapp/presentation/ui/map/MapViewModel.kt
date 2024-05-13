package com.mhamzasajjad.mapapp.presentation.ui.map

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhamzasajjad.mapapp.data.api.ApiEndPoints
import com.mhamzasajjad.mapapp.domain.model.location.DefaultLocation
import com.mhamzasajjad.mapapp.domain.model.location.UserLocationDetails
import com.mhamzasajjad.mapapp.domain.usecases.UserLocationUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val userLocationUpdateUseCase: UserLocationUpdateUseCase
): ViewModel() {

    private val _userCurrentLocation = MutableStateFlow(
        UserLocationDetails(
            latitude = DefaultLocation.defaultLatitude,
            longitude = DefaultLocation.defaultLongitude,
        )
    )
    val userCurrentLocation
        get() = _userCurrentLocation.asStateFlow()

    private val _mapStyleUrl = MutableStateFlow("")
    val mapStyleUrl
        get() = _mapStyleUrl.asStateFlow()

    private val _mapStyleIdList = listOf("outdoors", "stamen_terrain", "alidade_smooth_dark")
    private var _currentMapStyleIndex = 0

    private val _toastText = MutableStateFlow("")
    val toastText
        get() = _toastText.asStateFlow()

    private val _showToast = MutableStateFlow(false)
    val showToast
        get() = _showToast.asStateFlow()

    private val _refreshMap = MutableStateFlow(false)
    val refreshMap
        get() = _refreshMap.asStateFlow()

    init {
        setMapStyleUrl(
            isFirstTime = true
        )
    }

    fun setRefreshMap(refreshMap: Boolean) {
        _refreshMap.value = refreshMap
    }

    fun setMapStyleUrl(isFirstTime: Boolean) {
        if (_currentMapStyleIndex >= 0 && _currentMapStyleIndex < _mapStyleIdList.size) {
            val newMapStyle = _mapStyleIdList[_currentMapStyleIndex]
            _mapStyleUrl.value = ApiEndPoints.stadiaMapsStyleUrl.replace(
                "styleId",
                newMapStyle
            )
            if (!isFirstTime) {
                setRefreshMap(true)
                _toastText.value = "Map Mode: " +
                        newMapStyle.split("_").last().capitalize() + " Map"
                _showToast.value = true
            }
            _currentMapStyleIndex += 1
            if (_currentMapStyleIndex >= _mapStyleIdList.size) {
                _currentMapStyleIndex = 0
            }
        }
    }

    fun isLocationPermissionAlreadyGranted(context: Context): Boolean {
        val locationPermissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        return locationPermissions.any { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun updateUserCurrentLocation(locationDetails: UserLocationDetails) {
        if (_userCurrentLocation.value.altitude == 0.001) {
            locationDetails.altitude = 0.0
        } else {
            locationDetails.altitude = 0.001
        }
        _userCurrentLocation.value = locationDetails
    }

    fun onUserLocationButtonClicked() {
        viewModelScope.launch {
            userLocationUpdateUseCase.fetchLocationUpdates().collectLatest { userLocationDetails ->
                updateUserCurrentLocation(userLocationDetails)
                cancel()
            }
        }
    }

    fun hideToast() {
        _showToast.value = false
    }
}