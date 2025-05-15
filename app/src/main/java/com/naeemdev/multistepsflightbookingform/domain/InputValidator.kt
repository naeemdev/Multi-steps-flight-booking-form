package com.naeemdev.multistepsflightbookingform.domain

import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.AgeAbove18Rule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.BaseRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.EmailRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.ExpiryDateNotInPastRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.MinLengthRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.NoSpecialCharacterRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.NonEmptyRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.RegexRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.NoNumbersRule
import com.naeemdev.multistepsflightbookingform.domain.usecase.rules.PhoneNumberRule

class InputValidator(private val text: String) {

    private val rulesList = mutableListOf<BaseRule>()

    fun addRule(rule: BaseRule): InputValidator {
        rulesList.add(rule)
        return this
    }

    fun validate(): ValidationResult {
        for (rule in rulesList) {
            val result = rule.validate(text)
            if (result.not()) {
                return ValidationResult.Invalid(rule.getErrorMessage())
            }
        }
        return ValidationResult.Valid
    }


    fun nonEmpty(errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { NonEmptyRule(errorMsg) } ?: NonEmptyRule()
        addRule(rule)
        return this
    }

    fun minLength(length: Int, errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { MinLengthRule(length, errorMsg) } ?: MinLengthRule(length)
        addRule(rule)
        return this
    }

    fun validEmail(errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { EmailRule(errorMsg) } ?: EmailRule()
        addRule(rule)
        return this
    }

    fun noNumbers(errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { NoNumbersRule(errorMsg) } ?: NoNumbersRule()
        addRule(rule)
        return this
    }

    fun noSpecialCharacters(errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { NoSpecialCharacterRule(errorMsg) } ?: NoSpecialCharacterRule()
        addRule(rule)
        return this
    }

    fun regex(pattern: String, errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { RegexRule(pattern, errorMsg) } ?: RegexRule(pattern)
        addRule(rule)
        return this
    }

    fun ageAbove18Rule(errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { AgeAbove18Rule(errorMsg) } ?: AgeAbove18Rule()
        addRule(rule)
        return this
    }

    fun expiryDateNotInPastRule(errorMsg: Int? = null): InputValidator {
        val rule = errorMsg?.let { ExpiryDateNotInPastRule(errorMsg) } ?: ExpiryDateNotInPastRule()
        addRule(rule)
        return this
    }

    fun phoneNumberRule(
        countryIso: String = "",
        errorMsg: Int? = null
    ): InputValidator {
        val rule = errorMsg?.let {
            PhoneNumberRule(
                countryIso = countryIso,
                errorMsg = errorMsg
            )
        } ?: PhoneNumberRule()
        addRule(rule)
        return this
    }

}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errorMessageId: Int) : ValidationResult()
}
