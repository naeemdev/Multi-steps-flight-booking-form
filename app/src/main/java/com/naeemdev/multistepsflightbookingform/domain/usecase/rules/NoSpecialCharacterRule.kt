package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import com.naeemdev.multistepsflightbookingform.R
import java.util.regex.Pattern

class NoSpecialCharacterRule(var errorMsg: Int = R.string.should_not_contain_any_special_characters) :
    BaseRule {
    override fun validate(text: String): Boolean {
        if (text.isEmpty()) {
            return false
        }

        return !Pattern.compile("[\$&+,:;=\\\\?@#|/'<>.^*()%!-]").matcher(text).find()
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}
