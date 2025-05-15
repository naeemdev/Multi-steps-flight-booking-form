package com.naeemdev.multistepsflightbookingform.domain.repositories

import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD
import kotlinx.coroutines.flow.Flow


interface PassportFormatRepository {
    suspend fun getPassportFormatList(): Flow<Resource<List<PassportFormatD>>>

}