package com.mhamzasajjad.mapapp.domain.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserProfileResponse(
    @SerialName("status")
    val status: Int?,
    @SerialName("message")
    val message: String?,
)