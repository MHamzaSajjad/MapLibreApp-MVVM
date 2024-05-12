package com.mhamzasajjad.mapapp.di

import android.content.Context
import com.mhamzasajjad.mapapp.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesApiClient(@ApplicationContext context: Context) = ApiClient()
}