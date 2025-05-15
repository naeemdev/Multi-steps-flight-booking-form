package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

interface BaseRule {
    fun validate(text: String): Boolean
    fun getErrorMessage(): Int
    fun setError(msg: Int)
}
