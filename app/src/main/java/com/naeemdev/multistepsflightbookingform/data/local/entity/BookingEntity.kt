package com.naeemdev.multistepsflightbookingform.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dob: String,
    val nationality: String,
    val gender: String,
    val passportNumber: String?,
    val passportExpiryDate: String?,
    val email: String,
    val phone: String
)