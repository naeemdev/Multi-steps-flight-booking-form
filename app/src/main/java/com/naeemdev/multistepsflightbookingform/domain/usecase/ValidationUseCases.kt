package com.naeemdev.multistepsflightbookingform.domain.usecase

import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.domain.InputValidator
import com.naeemdev.multistepsflightbookingform.domain.ValidationResult
import com.naeemdev.multistepsflightbookingform.util.NAME_MIN_LENGTH
import jakarta.inject.Inject

class ValidationUseCases @Inject constructor() {

    // Generic function to handle common validation
    private fun validateField(
        fieldValue: String,
        vararg rules: (InputValidator) -> InputValidator
    ): ValidationResult {
        var validator = InputValidator(fieldValue)
        rules.forEach { rule ->
            validator = rule(validator)
        }
        return validator.validate()
    }

    fun validateName(name: String): ValidationResult {
        return validateField(
            name,
            { validator ->
                validator.nonEmpty(
                    errorMsg = R.string.error_name_empty
                )
            },
            { validator -> validator.minLength(NAME_MIN_LENGTH) },
            { validator -> validator.noSpecialCharacters() },
            { validator -> validator.noNumbers() }
        )
    }

    fun validateNationality(nationality: String): ValidationResult {
        return validateField(
            nationality,
            { validator ->
                validator.nonEmpty(
                    errorMsg = R.string.error_nationality_empty
                )
            }
        )
    }

    fun validateGender(gender: String): ValidationResult {
        return validateField(
            gender,
            { validator ->
                validator.nonEmpty(
                    errorMsg = R.string.please_select_gender
                )
            }
        )
    }

    fun validateDob(dob: String): ValidationResult {
        return validateField(
            dob,
            { validator -> validator.ageAbove18Rule() }
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return validateField(
            email,
            { validator ->
                validator.nonEmpty(
                    errorMsg = R.string.email_empty
                )
            },
            InputValidator::validEmail
        )
    }

    fun validatePhone(phone: String, countryIso: String): ValidationResult {
        return validateField(
            phone,
            { validator ->
                validator.nonEmpty(
                    errorMsg = R.string.error_phone_number
                )
            },
            { validator ->
                validator.phoneNumberRule(
                    countryIso = countryIso,
                    errorMsg = R.string.error_phone_number_invalid
                )
            }
        )
    }

    fun validatePassportNumber(passportNumber: String, format: String): ValidationResult {
        return validateField(
            passportNumber,
            { validator ->
                validator.nonEmpty(
                    errorMsg = R.string.error_passport_number_empty
                )
            },
            { validator ->
                validator.regex(
                    pattern = format,
                    errorMsg = R.string.error_passport_number_invalid
                )
            }
        )
    }

    fun validatePassportExpiryDate(expiryDate: String): ValidationResult {
        return validateField(
            expiryDate,
            { validator -> validator.nonEmpty() },
            { validator ->
                validator.expiryDateNotInPastRule(
                    errorMsg = R.string.error_passport_expiry_date_in_past
                )
            }
        )
    }
}