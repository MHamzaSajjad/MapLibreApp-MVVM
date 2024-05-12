package com.mhamzasajjad.mapapp.data.repository

import com.mhamzasajjad.mapapp.data.api.ApiClient
import com.mhamzasajjad.mapapp.data.utils.ErrorThrowable
import com.mhamzasajjad.mapapp.data.utils.Result
import com.mhamzasajjad.mapapp.domain.model.profile.UpdateUserProfileRequest
import com.mhamzasajjad.mapapp.domain.model.profile.UpdateUserProfileResponse
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
): IUserProfileRepository {
    override suspend fun updateUserProfile(userProfileRequest: UpdateUserProfileRequest): Result<UpdateUserProfileResponse> {
        // val result = apiClient.client.patch {
        //    url(
        //        ApiEndPoints.userProfileMockBaseUrl + ApiEndPoints.userProfileEndPoint + "/$userProfileRequest"
        //    )
        //    contentType(ContentType.Application.Json)
        //}.body<UpdateUserProfileResponse>()

        // ---------------------
        // To simulate a successful api response,
        // hence making success result object below for demonstration.
        // With an actual server, can uncomment above part
        // and replace baseUrl with real url

        val result = UpdateUserProfileResponse(
            status = 200,
            message = "Changes saved successfully"
        )

        return if (result.status == 200) {
            Result.Success(result)
        } else {
            Result.Error(
                ErrorThrowable(
                    errorCode = 400,
                    errorMessage = "Unable to connect"
                )
            )
        }
    }
}