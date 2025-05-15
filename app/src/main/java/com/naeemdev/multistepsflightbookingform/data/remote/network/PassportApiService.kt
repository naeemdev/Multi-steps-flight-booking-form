package com.naeemdev.multistepsflightbookingform.data.remote.network

import com.naeemdev.multistepsflightbookingform.data.remote.response.PassportFormatResponse
import retrofit2.Response
import retrofit2.http.GET

interface PassportApiService {

    @GET("passportFormats")
    suspend fun getPassportFormatsList(): Response<List<PassportFormatResponse>>
}