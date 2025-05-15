package com.naeemdev.multistepsflightbookingform.data.mapper

import com.naeemdev.multistepsflightbookingform.data.mapper.BookingMapper.mapToDomain
import com.naeemdev.multistepsflightbookingform.data.mapper.BookingMapper.mapToEntry
import com.naeemdev.multistepsflightbookingform.data.source.local.entity.BookingEntity
import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookingMapperTest {
    @Test
    fun `test mapToDomain with valid data`() {

        val bookingEntity = BookingEntity(
            nationality = "USA",
            id = 1,
            name = "John Doe",
            dob = "1990-01-01",
            gender = "Male",
            passportNumber = "A1234567",
            email = "john.doe@example.com",
            phone = "+123456789",
            passportExpiryDate = "2050-05-10"
        )


        val saveBookingD = bookingEntity.mapToDomain()

        assertEquals("USA", saveBookingD.nationality)
        assertEquals(1, saveBookingD.id)
        assertEquals("John Doe", saveBookingD.name)
        assertEquals("1990-01-01", saveBookingD.dob)
        assertEquals("Male", saveBookingD.gender)
        assertEquals("A1234567", saveBookingD.passportNumber)
        assertEquals("john.doe@example.com", saveBookingD.email)
        assertEquals("+123456789", saveBookingD.phone)
        assertEquals("2050-05-10", saveBookingD.passportExpiryDate)
    }

    @Test
    fun `test mapToDomain with missing fields in BookingEntity`() {

        val bookingEntity = BookingEntity(
            nationality = "",
            id = 0,
            name = "",
            dob = "",
            gender = "",
            passportNumber = "",
            email = "",
            phone = "",
            passportExpiryDate = ""
        )


        val saveBookingD = bookingEntity.mapToDomain()

        assertEquals("", saveBookingD.nationality)
        assertEquals(0, saveBookingD.id)
        assertEquals("", saveBookingD.name)
        assertEquals("", saveBookingD.dob)
        assertEquals("", saveBookingD.gender)
        assertEquals("", saveBookingD.passportNumber)
        assertEquals("", saveBookingD.email)
        assertEquals("", saveBookingD.phone)
        assertEquals("", saveBookingD.passportExpiryDate)
    }

    @Test
    fun `test mapToEntry with valid data`() {

        val saveBookingRequest = SaveBookingRequest(
            nationality = "Canada",
            name = "Jane Doe",
            dob = "1985-05-10",
            gender = "Female",
            passportNumber = "B9876543",
            email = "jane.doe@example.com",
            phone = "+987654321",
            passportExpiryDate = "2050-05-10"
        )


        val bookingEntity = saveBookingRequest.mapToEntry()

        assertEquals("Canada", bookingEntity.nationality)
        assertEquals("Jane Doe", bookingEntity.name)
        assertEquals("1985-05-10", bookingEntity.dob)
        assertEquals("Female", bookingEntity.gender)
        assertEquals("B9876543", bookingEntity.passportNumber)
        assertEquals("jane.doe@example.com", bookingEntity.email)
        assertEquals("+987654321", bookingEntity.phone)
        assertEquals("2050-05-10", bookingEntity.passportExpiryDate)
    }

    @Test
    fun `test mapToEntry with empty fields in SaveBookingRequest`() {

        val saveBookingRequest = SaveBookingRequest(
            nationality = "",
            name = "",
            dob = "",
            gender = "",
            passportNumber = "",
            email = "",
            phone = "",
            passportExpiryDate = ""
        )


        val bookingEntity = saveBookingRequest.mapToEntry()

        assertEquals("", bookingEntity.nationality)
        assertEquals("", bookingEntity.name)
        assertEquals("", bookingEntity.dob)
        assertEquals("", bookingEntity.gender)
        assertEquals("", bookingEntity.passportNumber)
        assertEquals("", bookingEntity.email)
        assertEquals("", bookingEntity.phone)
        assertEquals("", bookingEntity.passportExpiryDate)
    }

    @Test
    fun `test mapToDomain and mapToEntry with same data consistency`() {

        val saveBookingRequest = SaveBookingRequest(
            nationality = "Australia",
            name = "Alice",
            dob = "2000-12-12",
            gender = "Female",
            passportNumber = "C6543210",
            email = "alice@example.com",
            phone = "+123123123",
            passportExpiryDate = "2050-05-10"
        )


        val bookingEntity = saveBookingRequest.mapToEntry()
        val saveBookingD = bookingEntity.mapToDomain()

        assertEquals("Australia", saveBookingD.nationality)
        assertEquals("Alice", saveBookingD.name)
        assertEquals("2000-12-12", saveBookingD.dob)
        assertEquals("Female", saveBookingD.gender)
        assertEquals("C6543210", saveBookingD.passportNumber)
        assertEquals("alice@example.com", saveBookingD.email)
        assertEquals("+123123123", saveBookingD.phone)
        assertEquals("2050-05-10", bookingEntity.passportExpiryDate)
    }
}