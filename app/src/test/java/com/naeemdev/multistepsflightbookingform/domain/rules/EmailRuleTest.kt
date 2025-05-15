package com.naeemdev.multistepsflightbookingform.domain.rules

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.EmailRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class EmailRuleTest {
    private val emailRule = EmailRule(0)

    @Test
    fun `should return valid for a valid email address`() {
        val result = emailRule.validate("test@example.com")
        assertTrue(result)
    }

    @Test
    fun `should return invalid for an email without '@' symbol`() {
        val result = emailRule.validate("testexample.com")
        assertFalse(result)
    }

    @Test
    fun `should return invalid for an email without domain`() {
        val result = emailRule.validate("test@.com")
        assertFalse(result)
    }

    @Test
    fun `should return invalid for an email with invalid characters`() {
        val result = emailRule.validate("test@exam!ple.com")
        assertFalse(result)
    }

    @Test
    fun `should return invalid for an empty email address`() {
        val result = emailRule.validate("")
        assertFalse(result)
    }

    @Test
    fun `should return invalid for a null email address`() {
        val result = emailRule.validate(null ?: "")
        assertFalse(result)
    }
}