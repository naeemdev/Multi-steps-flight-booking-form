package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.PhoneNumberRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PhoneNumberRuleTest {

    private val rule = PhoneNumberRule(countryIso = "PK")

    @Test
    fun validateWhenPhoneNumberInvalidReturnFalse() {
        assertFalse(rule.validate("8108"))
    }

    @Test
    fun validateWhenPhoneNumberLongReturnFalse() {
        assertFalse(rule.validate("923136677777990990"))
    }

    @Test
    fun validateWhenPhoneNumberValidThenReturnTrue() {
        val result = rule.validate("+923136677777")
        assertTrue(result)
    }
}
