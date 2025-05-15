package com.naeemdev.multistepsflightbookingform.data.mapper


import com.naeemdev.multistepsflightbookingform.data.local.entity.BookingEntity
import com.naeemdev.multistepsflightbookingform.domain.model.SaveBookingD
import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest

object BookingMapper {

    fun BookingEntity.mapToDomain(): SaveBookingD {
        return SaveBookingD(
            nationality = nationality,
            id = id,
            name = name,
            dob = dob,
            gender = gender,
            passportNumber = passportNumber,
            email = email,
            phone = phone,
            passportExpiryDate = passportExpiryDate,
        )
    }

    fun SaveBookingRequest.mapToEntry(): BookingEntity {
        return BookingEntity(
            nationality = nationality,
            name = name,
            dob = dob,
            gender = gender,
            passportNumber = passportNumber,
            email = email,
            phone = phone,
            passportExpiryDate = passportExpiryDate,
        )
    }
}
