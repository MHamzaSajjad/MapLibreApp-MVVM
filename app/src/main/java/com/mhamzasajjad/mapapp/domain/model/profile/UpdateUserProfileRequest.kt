package com.mhamzasajjad.mapapp.domain.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserProfileRequest(
    @SerialName("user_id")
    val userId: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("birthday")
    val birthday: String,
    @SerialName("email")
    val email: String,
    @SerialName("is_subscribed")
    val isSubscribed: Boolean,
    @SerialName("phone")
    val phone: String
)
