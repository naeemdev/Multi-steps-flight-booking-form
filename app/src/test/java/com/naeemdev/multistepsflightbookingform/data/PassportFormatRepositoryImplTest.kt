package com.naeemdev.multistepsflightbookingform.data


import com.naeemdev.multistepsflightbookingform.data.mapper.PassportFormatMapper
import com.naeemdev.multistepsflightbookingform.data.repositories.PassportFormatRepositoryImpl
import com.naeemdev.multistepsflightbookingform.data.source.remote.network.PassportApiService
import com.naeemdev.multistepsflightbookingform.data.source.remote.response.PassportFormatResponse
import com.naeemdev.multistepsflightbookingform.domain.ErrorHandler.handleApiError
import com.naeemdev.multistepsflightbookingform.domain.ErrorType
import com.naeemdev.multistepsflightbookingform.domain.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.mockito.MockitoAnnotations
import kotlin.test.Test
import kotlin.test.assertEquals

class PassportFormatRepositoryImplTest {
    private lateinit var repository: PassportFormatRepositoryImpl
    private val apiService = mockk<PassportApiService>()
    private val mockResponse = listOf(
        PassportFormatResponse(
            nationality = "DE",
            format = "^[A-Z]{1,2}[A-Za-z0-9]{6,8}\$",
            example = "X1234567"
        ),
        PassportFormatResponse(
            nationality = "UK",
            format = "^[A-Z]{2}[0-9]{7}\$",
            example = "AB1234567"
        )
    )


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = PassportFormatRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    @Test
    fun `should return passport format list when API call is successful`() = runTest {
        coEvery { apiService.getPassportFormatsList() } returns mockk {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns mockResponse
        }
        val expectedDomainResponse =
            PassportFormatMapper.mapToDomainList(mockResponse)

        val result = repository.getPassportFormatList().last()
        assertTrue(result is Resource.Success)
        assertEquals(expectedDomainResponse, (result as Resource.Success).data)
    }

    @Test
    fun `test getUserList failure with error`() = runTest {
        coEvery { apiService.getPassportFormatsList() } returns mockk {
            coEvery { isSuccessful } returns false
            coEvery { handleApiError() } returns Resource.Error(ErrorType.UNAUTHORIZED)
        }
        val result = repository.getPassportFormatList().last()
        assertTrue(result is Resource.Error)
        coVerify { apiService.getPassportFormatsList() }
    }


}