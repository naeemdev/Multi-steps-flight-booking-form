package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.naeemdev.multistepsflightbookingform.R
import java.util.Locale

class PhoneNumberRule(
    var errorMsg: Int = R.string.invalid_phone_number_format,
    private val countryIso: String = ""
) : BaseRule {
    private val phoneNumberUtil: PhoneNumberUtil by lazy {
        PhoneNumberUtil.getInstance()
    }
    override fun validate(text: String): Boolean {
        return try {
            val defaultCountry = countryIso.ifEmpty { Locale.getDefault().country }
            val phoneNumber = phoneNumberUtil.parse(
                text,
                defaultCountry
            )
           val isValid= phoneNumberUtil.isValidNumber(phoneNumber)
            return isValid
        } catch (e: Exception) {
            false
        }
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}
