package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.NoNumbersRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NoNumberRuleTest {

    private val rule = NoNumbersRule()

    @Test
    fun validateWhenAllNumberReturnFalse() {
        assertFalse(rule.validate("09080989"))
    }

    @Test
    fun validateWhenHaveNumberReturnFalse() {
        assertFalse(rule.validate("7Flight Booking"))
    }

    @Test
    fun validateWhenNoNumberThenReturnTrue() {
        assertTrue(rule.validate("Flight Booking"))
    }

}
