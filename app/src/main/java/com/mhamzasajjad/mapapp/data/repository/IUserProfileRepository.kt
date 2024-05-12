package com.mhamzasajjad.mapapp.data.repository

import com.mhamzasajjad.mapapp.data.utils.Result
import com.mhamzasajjad.mapapp.domain.model.profile.UpdateUserProfileRequest
import com.mhamzasajjad.mapapp.domain.model.profile.UpdateUserProfileResponse

interface IUserProfileRepository {
    suspend fun updateUserProfile(userProfileRequest: UpdateUserProfileRequest): Result<UpdateUserProfileResponse>
}