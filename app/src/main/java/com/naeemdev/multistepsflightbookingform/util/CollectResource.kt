package com.naeemdev.multistepsflightbookingform.util

import com.naeemdev.multistepsflightbookingform.domain.ErrorType
import com.naeemdev.multistepsflightbookingform.domain.Resource
import kotlinx.coroutines.flow.Flow

suspend inline fun <T> Flow<Resource<T>>.collectResource(
    crossinline onLoading: () -> Unit,
    crossinline onSuccess: (T) -> Unit,
    crossinline onError: (ErrorType) -> Unit
) {
    collect { resource ->
        when (resource) {
            is Resource.Loading -> onLoading()
            is Resource.Success -> onSuccess(resource.data)
            is Resource.Error -> onError(resource.errorType)
        }
    }
}
