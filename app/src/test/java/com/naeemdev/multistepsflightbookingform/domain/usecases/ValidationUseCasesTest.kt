package com.naeemdev.multistepsflightbookingform.domain.usecases


import com.naeemdev.multistepsflightbookingform.domain.ValidationResult
import com.naeemdev.multistepsflightbookingform.domain.usecase.ValidationUseCases
import org.junit.Assert.assertTrue
import java.time.LocalDate
import kotlin.test.Test

class ValidationUseCasesTest {
    private val validationUseCases = ValidationUseCases()

    @Test
    fun `should return invalid if name is empty`() {
        val result = validationUseCases.validateName("")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if name is too short`() {
        val result = validationUseCases.validateName("nae")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if name contains special characters`() {
        val result = validationUseCases.validateName("Naeem@")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if name contains numbers`() {
        val result = validationUseCases.validateName("Naeem123")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if nationality is empty`() {
        val result = validationUseCases.validateNationality("")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return valid if all passenger data is valid`() {
        val result = validationUseCases.validateDob(LocalDate.now().minusYears(20).toString())
        assertTrue(result is ValidationResult.Valid)
    }

    @Test
    fun `should return invalid if dob is under 18 years old`() {
        val result = validationUseCases.validateDob(LocalDate.now().minusYears(17).toString())
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if email is empty`() {
        val result = validationUseCases.validateEmail("")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if phone is empty`() {
        val result = validationUseCases.validatePhone("", "Pk")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if phone contains special characters`() {
        val result = validationUseCases.validatePhone("123-456-7890", "PK")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return valid if phone is valid`() {
        val result = validationUseCases.validatePhone("+923136573732", "PK")
        assertTrue(result is ValidationResult.Valid)
    }

    @Test
    fun `should return invalid if gender is empty`() {
        val result = validationUseCases.validateGender("")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return valid if gender is not empty`() {
        val result = validationUseCases.validateGender("Male")
        assertTrue(result is ValidationResult.Valid)
    }

    @Test
    fun `should return invalid if passport number is  empty`() {
        val result = validationUseCases.validatePassportNumber("", "")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return invalid if passport number if  pass wrong passport format`() {
        val result = validationUseCases.validatePassportNumber("Ab23213", "^[A-Z][0-9]{7}\$")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `should return valid if passport number is valid`() {
        val result = validationUseCases.validatePassportNumber("A1234567", "^[A-Z][0-9]{7}\$")
        assertTrue(result is ValidationResult.Valid)
    }

    @Test
    fun `validatePassportExpiryDate should return error when expiry date is empty`() {
        val result = validationUseCases.validatePassportExpiryDate("")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `validatePassportExpiryDate should return error when expiry date is in the past`() {
        val result = validationUseCases.validatePassportExpiryDate("2020-01-01")
        assertTrue(result is ValidationResult.Invalid)
    }

    @Test
    fun `validatePassportExpiryDate should return valid when expiry date is valid`() {
        val result =
            validationUseCases.validatePassportExpiryDate(LocalDate.now().plusYears(10).toString())
        assertTrue(result is ValidationResult.Valid)
    }
}