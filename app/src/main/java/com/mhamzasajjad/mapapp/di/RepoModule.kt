package com.mhamzasajjad.mapapp.di

import android.content.Context
import com.mhamzasajjad.mapapp.data.api.ApiClient
import com.mhamzasajjad.mapapp.data.repository.UserProfileRepositoryImpl
import com.mhamzasajjad.mapapp.domain.usecases.UpdateUserProfileUseCase
import com.mhamzasajjad.mapapp.domain.usecases.UserLocationUpdateUseCase
import com.mhamzasajjad.mapapp.presentation.ui.map.MapViewModel
import com.mhamzasajjad.mapapp.presentation.ui.profile.UserProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providesUserLocationUpdateUseCase(@ApplicationContext context: Context) =
        UserLocationUpdateUseCase(context = context)

    @Singleton
    @Provides
    fun providesUpdateUserProfileUseCase(userProfileRepository: UserProfileRepositoryImpl) =
        UpdateUserProfileUseCase(userProfileRepository = userProfileRepository)

    @Singleton
    @Provides
    fun providesMapViewModel(userLocationUpdateUseCase: UserLocationUpdateUseCase) =
        MapViewModel(userLocationUpdateUseCase = userLocationUpdateUseCase)


    @Singleton
    @Provides
    fun providesUserProfileViewModel(updateUserProfileUseCase: UpdateUserProfileUseCase) =
        UserProfileViewModel(updateUserProfileUseCase = updateUserProfileUseCase)


    @Singleton
    @Provides
    fun providesApiClient(apiClient: ApiClient) = UserProfileRepositoryImpl(
        apiClient = apiClient,
    )
}