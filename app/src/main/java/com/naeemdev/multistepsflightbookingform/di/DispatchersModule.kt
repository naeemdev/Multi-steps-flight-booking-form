package com.naeemdev.multistepsflightbookingform.di

import com.naeemdev.multistepsflightbookingform.dispatcher.DispatchersProvider
import com.naeemdev.multistepsflightbookingform.dispatcher.DispatchersProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {
    @Binds
    abstract fun provideDispatchersProvider(dispatcher: DispatchersProviderImpl): DispatchersProvider
}
