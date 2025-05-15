package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.RegexRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegexRuleTest {
    // any custom regex
    private val rule = RegexRule("^[^\\d].*")

    // returns false if the string have a number at the beginning
    @Test
    fun validateWhenHaveNumberAtBeginningReturnFalse() {
        assertFalse(rule.validate("4Wego"))
    }

    // returns true if the string does not have a number at the beginning
    @Test
    fun validateWhenNoNumberAtBeginningThenReturnTrue() {
        val result = rule.validate("Wego")
        assertTrue(result)
    }

}
