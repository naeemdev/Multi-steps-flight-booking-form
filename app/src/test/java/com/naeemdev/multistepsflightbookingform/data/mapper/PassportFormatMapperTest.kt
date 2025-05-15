package com.naeemdev.multistepsflightbookingform.data.mapper

import com.naeemdev.multistepsflightbookingform.data.mapper.PassportFormatMapper.mapToDomain
import com.naeemdev.multistepsflightbookingform.data.source.remote.response.PassportFormatResponse
import org.junit.Assert.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class PassportFormatMapperTest {

    @Test
    fun `test mapToDomain with valid data`() {

        val passportFormatResponse = PassportFormatResponse(
            nationality = "USA",
            format = "123456789",
            example = "A1234567"
        )

        val passportFormatD = passportFormatResponse.mapToDomain()

        assertEquals("USA", passportFormatD.nationality)
        assertEquals("123456789", passportFormatD.format)
        assertEquals("A1234567", passportFormatD.example)
    }

    @Test
    fun `test mapToDomain with null fields`() {
        val passportFormatResponse = PassportFormatResponse(
            nationality = null,
            format = null,
            example = null
        )


        val passportFormatD = passportFormatResponse.mapToDomain()

        assertEquals("", passportFormatD.nationality)
        assertEquals("", passportFormatD.format)
        assertEquals("", passportFormatD.example)
    }

    @Test
    fun `test mapToDomain with mixed valid and null fields`() {
        val passportFormatResponse = PassportFormatResponse(
            nationality = "USA",
            format = null,
            example = "A1234567"
        )

        val passportFormatD = passportFormatResponse.mapToDomain()


        assertEquals("USA", passportFormatD.nationality)
        assertEquals("", passportFormatD.format)
        assertEquals("A1234567", passportFormatD.example)
    }

    @Test
    fun `test mapToDomainList with multiple passport formats`() {

        val passportFormatResponseList = listOf(
            PassportFormatResponse("USA", "123456789", "A1234567"),
            PassportFormatResponse("Canada", "987654321", "B9876543"),
            PassportFormatResponse(null, null, null)
        )


        val passportFormatDList = PassportFormatMapper.mapToDomainList(passportFormatResponseList)


        assertEquals(3, passportFormatDList.size)

        assertEquals("USA", passportFormatDList[0].nationality)
        assertEquals("123456789", passportFormatDList[0].format)
        assertEquals("A1234567", passportFormatDList[0].example)

        assertEquals("Canada", passportFormatDList[1].nationality)
        assertEquals("987654321", passportFormatDList[1].format)
        assertEquals("B9876543", passportFormatDList[1].example)

        assertEquals("", passportFormatDList[2].nationality)
        assertEquals("", passportFormatDList[2].format)
        assertEquals("", passportFormatDList[2].example)
    }

    @Test
    fun `test mapToDomainList with empty list`() {
        val emptyList = listOf<PassportFormatResponse>()
        val result = PassportFormatMapper.mapToDomainList(emptyList)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `test mapToDomainList with all null fields`() {

        val passportFormatResponseList = listOf(
            PassportFormatResponse(null, null, null),
            PassportFormatResponse(null, null, null)
        )

        val result = PassportFormatMapper.mapToDomainList(passportFormatResponseList)

        result.forEach {
            assertEquals("", it.nationality)
            assertEquals("", it.format)
            assertEquals("", it.example)
        }
    }
}