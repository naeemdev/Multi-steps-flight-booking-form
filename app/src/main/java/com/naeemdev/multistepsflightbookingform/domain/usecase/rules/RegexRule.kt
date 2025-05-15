package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import com.naeemdev.multistepsflightbookingform.R

class RegexRule(
    private val pattern: String,
    var errorMsg: Int = R.string.regex_pattern_doesn_t_match
) : BaseRule {

    override fun validate(text: String): Boolean {
        return text.matches(Regex(pattern))
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}
