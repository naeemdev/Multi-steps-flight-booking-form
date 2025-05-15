package com.naeemdev.multistepsflightbookingform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naeemdev.multistepsflightbookingform.data.local.entity.BookingEntity

@Dao
interface BookingDao {

    @Query("SELECT * FROM bookingentity ORDER BY id DESC LIMIT 1")
    fun getBookingById(): BookingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("DELETE FROM bookingentity")
    suspend fun deleteAllBooking()
}