package com.mhamzasajjad.mapapp.presentation.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.mhamzasajjad.mapapp.R

sealed class BottomNavBarItem(
    var tabRoute: String,
    var tabIconId: ImageVector,
    @StringRes var tabLabelResId: Int
) {
    data object Map : BottomNavBarItem(
        tabRoute = "map",
        tabIconId = Icons.Outlined.Place,
        tabLabelResId = R.string.map_tab_label,
    )

    data object Profile : BottomNavBarItem(
        tabRoute = "profile",
        tabIconId = Icons.Outlined.Person,
        tabLabelResId = R.string.profile_tab_label,
    )

    companion object {
        val bottomNavBarItems: List<BottomNavBarItem>
            get() = listOf(Map, Profile)
    }
}