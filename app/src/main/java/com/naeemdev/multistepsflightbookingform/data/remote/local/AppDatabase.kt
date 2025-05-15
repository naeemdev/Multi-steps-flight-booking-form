package com.naeemdev.multistepsflightbookingform.data.remote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naeemdev.multistepsflightbookingform.data.remote.local.dao.BookingDao
import com.naeemdev.multistepsflightbookingform.data.remote.local.entity.BookingEntity


@Database(
    entities = [
        BookingEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao

}

