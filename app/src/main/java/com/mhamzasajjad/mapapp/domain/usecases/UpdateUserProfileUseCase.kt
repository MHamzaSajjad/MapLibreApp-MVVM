package com.mhamzasajjad.mapapp.domain.usecases

import com.mhamzasajjad.mapapp.data.repository.IUserProfileRepository
import com.mhamzasajjad.mapapp.domain.model.profile.UpdateUserProfileRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userProfileRepository: IUserProfileRepository
) {

    suspend operator fun invoke(updateUserProfileRequest: UpdateUserProfileRequest) = flow {
        val response = userProfileRepository.updateUserProfile(updateUserProfileRequest)
        emit(response)
    }
}