package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.NoSpecialCharacterRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NoSpecialCharacterRuleTest {

    private val rule = NoSpecialCharacterRule()

    @Test
    fun validateWhenHaveSpecialCharacterReturnFalse() {
        assertFalse(rule.validate("@Wego"))
    }

    @Test
    fun validateWhenNotHaveSpecialCharacterThenReturnTrue() {
        assertTrue(rule.validate("wego"))
    }

}
