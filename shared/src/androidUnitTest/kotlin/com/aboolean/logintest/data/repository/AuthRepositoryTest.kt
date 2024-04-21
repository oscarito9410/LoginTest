package com.aboolean.logintest.data.repository

import app.cash.turbine.test
import com.aboolean.logintest.MockData.VALID_EMAIL
import com.aboolean.logintest.MockData.VALID_PASSWORD
import com.aboolean.logintest.data.datasource.AuthDataSource
import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.data.mapper.toDomain
import com.aboolean.logintest.data.model.AuthResponse
import com.aboolean.logintest.domain.repository.AuthRepository
import com.aboolean.logintest.foundation.OperationResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(
    ExperimentalCoroutinesApi::class
)
class AuthRepositoryTest {

    private val authDataSource: AuthDataSource = mockk(relaxed = true)

    // suit
    private lateinit var repository: AuthRepository

    // dispatchers
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        repository = AuthRepositoryImpl(
            authDataSource,
            dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `doLogin when dataSource returns OperationResult_Success`() = scope.runTest {
        // given
        val authResponse = AuthResponse(
            isAuthenticated = true,
            userName = "Oscar",
            email = VALID_EMAIL,
            accessToken = "access token"
        )
        coEvery { authDataSource.login(any()) } returns OperationResult.Success(
            authResponse
        )
        // when
        val flow = repository.doLogin(
            VALID_EMAIL,
            VALID_PASSWORD
        )
        // then
        flow.test {
            val result = awaitItem()
            assertTrue(result is OperationResult.Success)
            assertEquals(authResponse.toDomain(), result.data)
            awaitComplete()
        }
    }

    @Test
    fun `doLogin when dataSource returns OperationResult_Error`() = scope.runTest {
        // given
        val exception = InvalidCredentialException()

        coEvery { authDataSource.login(any()) } returns OperationResult.Error(
            exception
        )
        // when
        val flow = repository.doLogin(
            VALID_EMAIL,
            VALID_PASSWORD
        )
        // then
        flow.test {
            val result = awaitItem()
            assertTrue(result is OperationResult.Error)
            assertEquals(exception, result.exception)
            awaitComplete()
        }
    }
}