package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.ExpiryDateNotInPastRule
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExpiryDateNotInPastRuleTest {

    private val rule = ExpiryDateNotInPastRule()

    @Test
    fun `expiry date should be in the future`() {
        val futureDate = LocalDate.now().plusDays(1).toString()
        val isValid = rule.validate(futureDate)
        assertTrue(isValid, "The expiry date should be valid if it is in the future.")
    }

    @Test
    fun `expiry date should not be in the past`() {
        val pastDate = LocalDate.now().minusDays(1).toString()

        val isValid = rule.validate(pastDate)
        assertFalse(isValid, "The expiry date should be invalid if it is in the past.")
    }

    @Test
    fun `expiry date should not be today`() {
        val todayDate = LocalDate.now().toString()
        val isValid = rule.validate(todayDate)

        assertFalse(isValid, "The expiry date should be invalid if it is today.")
    }

}