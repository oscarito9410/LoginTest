package com.aboolean.logintest.domain.usecase

import app.cash.turbine.test
import com.aboolean.logintest.MockData.VALID_ACCESS_TOKEN
import com.aboolean.logintest.MockData.VALID_EMAIL
import com.aboolean.logintest.MockData.VALID_PASSWORD
import com.aboolean.logintest.MockData.VALID_USER_NAME
import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.domain.entities.UserEntity
import com.aboolean.logintest.domain.repository.AuthRepository
import com.aboolean.logintest.foundation.OperationResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
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


class DoLoginUseCaseTest {

    // mock
    private val authRepository: AuthRepository = mockk()

    // dispatchers
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    // suit
    private lateinit var doLoginUseCase: DoLoginUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        doLoginUseCase = DoLoginUseCase(
            authRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `invoke when repository returns OperationResult_Success`() = scope.runTest {
        // given
        val userEntity = UserEntity(
            isAuthenticated = true,
            userName = VALID_USER_NAME,
            email = VALID_EMAIL,
            accessToken = VALID_ACCESS_TOKEN
        )

        coEvery { authRepository.doLogin(VALID_EMAIL, VALID_PASSWORD) } returns flowOf(
            OperationResult.Success(userEntity)
        )

        // when
        val flow = doLoginUseCase.invoke(VALID_EMAIL, VALID_PASSWORD)

        // then
        coVerify { authRepository.doLogin(VALID_EMAIL, VALID_PASSWORD) }
        flow.test {
            val result = awaitItem()
            assertTrue(result is OperationResult.Success)
            assertEquals(userEntity, result.data)
            awaitComplete()
        }
    }

    @Test
    fun `invoke when repository returns OperationResult_Error`() = scope.runTest {
        // given
        val exception = InvalidCredentialException()

        coEvery { authRepository.doLogin(VALID_EMAIL, VALID_PASSWORD) } returns flowOf(
            OperationResult.Error(exception)
        )

        // when
        val flow = doLoginUseCase.invoke(VALID_EMAIL, VALID_PASSWORD)

        // then
        coVerify { authRepository.doLogin(VALID_EMAIL, VALID_PASSWORD) }
        flow.test {
            val result = awaitItem()
            assertTrue(result is OperationResult.Error)
            assertEquals(exception, result.exception)
            awaitComplete()
        }
    }
}
