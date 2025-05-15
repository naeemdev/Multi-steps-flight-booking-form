package com.naeemdev.multistepsflightbookingform.di

import com.naeemdev.multistepsflightbookingform.data.remote.local.dao.BookingDao
import com.naeemdev.multistepsflightbookingform.data.remote.network.PassportApiService
import com.naeemdev.multistepsflightbookingform.data.repositories.BookingRepositoryImpl
import com.naeemdev.multistepsflightbookingform.data.repositories.PassportFormatRepositoryImpl
import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import com.naeemdev.multistepsflightbookingform.domain.repositories.PassportFormatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePassportRepository(apiService: PassportApiService): PassportFormatRepository {
        return PassportFormatRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideBookingRepository(bookingDao: BookingDao): BookingRepository {
        return BookingRepositoryImpl(bookingDao)
    }

}