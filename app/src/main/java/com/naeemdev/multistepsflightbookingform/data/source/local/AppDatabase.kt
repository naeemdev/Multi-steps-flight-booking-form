package com.naeemdev.multistepsflightbookingform.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naeemdev.multistepsflightbookingform.data.source.local.dao.BookingDao
import com.naeemdev.multistepsflightbookingform.data.source.local.entity.BookingEntity


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

