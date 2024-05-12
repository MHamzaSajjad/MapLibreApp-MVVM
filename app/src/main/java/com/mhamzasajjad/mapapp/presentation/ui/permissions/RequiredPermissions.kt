package com.mhamzasajjad.mapapp.presentation.ui.permissions

enum class RequiredPermission(
    val permission: String,
    val title: String,
    val description: String,
    val permanentlyDeniedDescription: String,
) {
    COARSE_LOCATION(
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION,
        title = "Approximate Location Permission",
        description = "This permission is needed to get your approximate location. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to get your approximate location. Please grant the permission in app settings.",
    ),

    FINE_LOCATION(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
        title = "Precise Location Permission",
        description = "This permission is needed to get your precise location. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to get your precise location. Please grant the permission in app settings.",
    );

    fun getPermissionText(isPermanentlyDenied: Boolean): String {
        return if (isPermanentlyDenied) this.permanentlyDeniedDescription else this.description
    }
}

fun getRequiredPermission(permission: String): RequiredPermission? {
    return RequiredPermission.values().find { it.permission == permission }
}