package com.mhamzasajjad.mapapp.domain.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val fullName: String = "",
    val birthday: String = "",
    val email: String = "",
    val phone: String = "",
    val isSubscribed: Boolean = false,
)