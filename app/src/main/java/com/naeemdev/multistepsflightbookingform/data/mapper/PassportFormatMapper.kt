package com.naeemdev.multistepsflightbookingform.data.mapper


import com.naeemdev.multistepsflightbookingform.data.source.remote.response.PassportFormatResponse
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD

object PassportFormatMapper {

    // Convert a PassportFormatResponse (DTO) to a PassportFormatD (Domain Model)
    fun PassportFormatResponse.mapToDomain(): PassportFormatD {
        return PassportFormatD(
            nationality = nationality.orEmpty(),
            format = format.orEmpty(),
            example = example.orEmpty()
        )
    }

    // Convert a list of PassportFormatResponse (DTO) to a list of PassportFormatD (Domain Model)
    fun mapToDomainList(userModels: List<PassportFormatResponse>): List<PassportFormatD> {
        return userModels.map { it.mapToDomain() }
    }

}
