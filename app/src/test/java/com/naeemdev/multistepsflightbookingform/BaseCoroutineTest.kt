package com.naeemdev.multistepsflightbookingform

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseCoroutineTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @OptIn(ExperimentalSerializationApi::class)
    protected val json by lazy {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
            explicitNulls = false
            decodeEnumsCaseInsensitive = true
        }
    }

    @Before
    fun before() {
        setup()
    }

    open fun setup() {
    }
}
