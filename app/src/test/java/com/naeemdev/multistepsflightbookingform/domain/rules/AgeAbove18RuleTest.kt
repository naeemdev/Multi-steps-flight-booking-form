package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.AgeAbove18Rule
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AgeAbove18RuleTest {
    private val rule = AgeAbove18Rule()

    @Test
    fun `should return true if age is 18 or above`() {
        val dob = LocalDate.now().minusYears(18).toString()
        val result = rule.validate(dob)
        assertTrue(result, "Age should be 18 or above")
    }

    @Test
    fun `should return false if age is below 18`() {
        val dob = LocalDate.now().minusYears(17).toString()
        val result = rule.validate(dob)
        assertFalse(result, "Age should be 18 or above")
    }

}