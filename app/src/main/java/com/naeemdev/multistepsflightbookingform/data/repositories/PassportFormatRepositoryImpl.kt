package com.naeemdev.multistepsflightbookingform.data.repositories


import com.naeemdev.multistepsflightbookingform.data.mapper.PassportFormatMapper
import com.naeemdev.multistepsflightbookingform.data.remote.network.PassportApiService
import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD
import com.naeemdev.multistepsflightbookingform.domain.repositories.PassportFormatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PassportFormatRepositoryImpl @Inject constructor(
    private val apiService: PassportApiService,
) : PassportFormatRepository {

    override suspend fun getPassportFormatList(): Flow<Resource<List<PassportFormatD>>> {
        return safeApiCall(
            apiCall = {
                val response = apiService.getPassportFormatsList()
                response

            },
            response = { apiResponse ->
                apiResponse.body()?.let {
                    PassportFormatMapper.mapToDomainList(it)
                } ?: emptyList()
            }
        )
    }
}