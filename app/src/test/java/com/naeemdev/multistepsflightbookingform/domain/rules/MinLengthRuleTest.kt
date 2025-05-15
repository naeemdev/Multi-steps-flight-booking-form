package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.MinLengthRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MinLengthRuleTest {

    private val rule = MinLengthRule(8)

    @Test
    fun validateWhenMinLengthNotEightThenReturnFalse() {
        assertFalse(rule.validate("wego"))
    }

    @Test
    fun validateWhenMinLengthEightThenReturnTrue() {
        assertTrue(rule.validate("wego flight booking"))
    }

}
