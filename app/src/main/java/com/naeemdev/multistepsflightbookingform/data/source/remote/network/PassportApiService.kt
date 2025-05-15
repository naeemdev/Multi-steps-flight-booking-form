package com.naeemdev.multistepsflightbookingform.data.source.remote.network

import com.naeemdev.multistepsflightbookingform.data.source.remote.response.PassportFormatResponse
import retrofit2.Response
import retrofit2.http.GET

interface PassportApiService {

    @GET("passportFormats")
    suspend fun getPassportFormatsList(): Response<List<PassportFormatResponse>>
}