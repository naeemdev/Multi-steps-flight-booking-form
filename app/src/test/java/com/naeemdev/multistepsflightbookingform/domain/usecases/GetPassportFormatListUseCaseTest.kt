package com.naeemdev.multistepsflightbookingform.domain.usecases

import com.naeemdev.multistepsflightbookingform.domain.ErrorType
import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD
import com.naeemdev.multistepsflightbookingform.domain.repositories.PassportFormatRepository
import com.naeemdev.multistepsflightbookingform.domain.usecase.GetPassportFormatListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPassportFormatListUseCaseTest {
    private lateinit var passportFormatRepository: PassportFormatRepository
    private lateinit var getPassportFormatListUseCase: GetPassportFormatListUseCase

    @Before
    fun setup() {
        passportFormatRepository = mockk()
        getPassportFormatListUseCase = GetPassportFormatListUseCase(passportFormatRepository)
    }

    @Test
    fun `test invoke when passport formats are successfully fetched`() = runTest {
        val mockPassportFormatList = listOf(
            PassportFormatD(
                nationality = "USA",
                format = "MM-DD-YYYY",
                example = "12-15-1990"
            ),
            PassportFormatD(
                nationality = "UK",
                format = "DD-MM-YYYY",
                example = "15-12-1990"
            )
        )
        coEvery { passportFormatRepository.getPassportFormatList() } returns flowOf(
            Resource.Success(
                mockPassportFormatList
            )
        )

        val resultFlow = getPassportFormatListUseCase.invoke()

        resultFlow.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    assertEquals(2, resource.data.size)
                    assertEquals("USA", resource.data.first().nationality)
                    assertEquals("MM-DD-YYYY", resource.data.first().format)
                    assertEquals("12-15-1990", resource.data.first().example)
                }

                else -> throw AssertionError("Expected Resource.Success")
            }
        }

        coVerify { passportFormatRepository.getPassportFormatList() }
    }

    @Test
    fun `test invoke when passport format list is empty`() = runTest {
        coEvery { passportFormatRepository.getPassportFormatList() } returns flowOf(
            Resource.Success(
                emptyList()
            )
        )

        val resultFlow = getPassportFormatListUseCase.invoke()

        resultFlow.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    assertTrue(resource.data.isEmpty())
                }

                else -> throw AssertionError("Expected Resource.Success")
            }
        }
        coVerify { passportFormatRepository.getPassportFormatList() }
    }

    @Test
    fun `test invoke when passport format list fetch fails`() = runTest {
        coEvery { passportFormatRepository.getPassportFormatList() } returns flowOf(
            Resource.Error(
                ErrorType.API_RESPONSE_NULL
            )
        )

        val resultFlow = getPassportFormatListUseCase.invoke()

        resultFlow.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    assertEquals(ErrorType.API_RESPONSE_NULL, resource.errorType)
                }

                else -> throw AssertionError("Expected Resource.Error")
            }
        }

        coVerify { passportFormatRepository.getPassportFormatList() }
    }

}