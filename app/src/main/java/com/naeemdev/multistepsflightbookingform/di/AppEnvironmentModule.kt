package com.naeemdev.multistepsflightbookingform.di

import com.naeemdev.multistepsflightbookingform.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppEnvironmentModule {
    @Provides
    @Singleton
    @Named("isInternational")
    fun provideInternationalFlag(): Boolean = BuildConfig.IS_INTERNATIONAL
}