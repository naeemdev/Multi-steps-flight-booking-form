package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.NonEmptyRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NonEmptyRuleTest {

    private val rule = NonEmptyRule()

    @Test
    fun validateWhenEmptyThenReturnFalse() {
        assertFalse(rule.validate(""))
    }

    @Test
    fun validateWhenNotEmptyThenReturnTrue() {
        assertTrue(rule.validate("test"))
    }

}
