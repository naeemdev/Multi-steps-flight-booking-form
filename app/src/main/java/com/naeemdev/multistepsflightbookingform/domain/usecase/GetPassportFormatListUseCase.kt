package com.naeemdev.multistepsflightbookingform.domain.usecase


import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD
import com.naeemdev.multistepsflightbookingform.domain.repositories.PassportFormatRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPassportFormatListUseCase @Inject constructor(
    private val repository: PassportFormatRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<PassportFormatD>>> {
        return repository.getPassportFormatList()
    }
}