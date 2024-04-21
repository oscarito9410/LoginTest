package com.aboolean.logintest.presentation

import com.aboolean.logintest.MockData.INVALID_CREDENTIALS_ERROR
import com.aboolean.logintest.MockData.INVALID_EMAIL
import com.aboolean.logintest.MockData.INVALID_PASSWORD
import com.aboolean.logintest.MockData.UNHANDLED_ERROR_MESSAGE
import com.aboolean.logintest.MockData.VALID_EMAIL
import com.aboolean.logintest.MockData.VALID_PASSWORD
import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.domain.entities.UserEntity
import com.aboolean.logintest.domain.usecase.DoLoginUseCase
import com.aboolean.logintest.foundation.OperationResult
import com.aboolean.logintest.presentation.manager.ResourceManager
import com.aboolean.logintest.presentation.screens.login.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import logintest.shared.generated.resources.Res
import logintest.shared.generated.resources.error_service_auth
import logintest.shared.generated.resources.error_unhandled
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(
    ExperimentalResourceApi::class,
    ExperimentalCoroutinesApi::class
)
class LoginViewModelTest {

    private val useCase: DoLoginUseCase = mockk(relaxed = true)
    private val resourceManager: ResourceManager = mockk(relaxed = true)

    // suit
    private lateinit var viewModel: LoginViewModel

    // dispatchers
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(
            useCase,
            resourceManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `onLoginPressed when email isInvalid`() {
        viewModel.apply {
            // given
            updateEmail(INVALID_EMAIL)
            // when
            onLoginPressed()
            // then
            assertTrue(state.isInvalidEmail)
        }
    }

    @Test
    fun `onLoginPressed when email isValid`() {
        viewModel.apply {
            // given
            updateEmail(VALID_EMAIL)
            // when
            onLoginPressed()
            // then
            assertFalse(state.isInvalidEmail)
        }
    }

    @Test
    fun `onLoginPressed when password doesn't meet criteria`() {
        viewModel.apply {
            // given
            updateEmail(VALID_EMAIL)
            updatePassword(INVALID_PASSWORD)
            // when
            onLoginPressed()
            // then
            assertTrue(state.isInvalidPassword)
        }
    }

    @Test
    fun `onLoginPressed when password meets criteria`() {
        viewModel.apply {
            // given
            updatePassword(VALID_PASSWORD)
            // when
            onLoginPressed()
            // then
            assertFalse(state.isInvalidPassword)
        }
    }

    @Test
    fun `resetLoginState works as expected`() {
        viewModel.apply {
            // when
            resetLoginState()
            // then
            assertFalse(state.isLoading)
            assertNull(state.errorMessage)
        }
    }

    @Test
    fun `executeLoginUseCase when is OperationResult_Success`() =
        scope.runTest {
            // given
            val userEntity = UserEntity(
                isAuthenticated = true,
                userName = "Oscar P",
                email = "oscar@gmail.com",
                accessToken = "access token"
            )

            val flowResponse = flow {
                emit(
                    OperationResult.Success(
                        userEntity
                    )
                )
            }
            coEvery { useCase.invoke(any(), any()) } coAnswers {
                flowResponse
            }

            // when
            viewModel.apply {
                updatePassword(VALID_PASSWORD)
                updateEmail(VALID_EMAIL)
                executeLoginUseCase()
            }
            // then
            viewModel.apply {
                advanceUntilIdle()
                assertNotNull(state.userResult)
                assertEquals(userEntity, state.userResult)
            }
        }

    @Test
    fun `executeLoginUseCase when is OperationResult_Error InvalidCredentialException`() =
        scope.runTest {
            // given
            val flowResponse = flow {
                emit(
                    OperationResult.Error(
                        InvalidCredentialException()
                    )
                )
            }
            coEvery { useCase.invoke(any(), any()) } coAnswers {
                flowResponse
            }

            coEvery {
                resourceManager.getString(Res.string.error_service_auth)
            } returns INVALID_CREDENTIALS_ERROR

            // when
            viewModel.apply {
                updatePassword(VALID_PASSWORD)
                updateEmail(VALID_EMAIL)
                executeLoginUseCase()
            }
            // then
            viewModel.apply {
                advanceUntilIdle()
                assertNotNull(state.errorMessage)
                assertEquals(INVALID_CREDENTIALS_ERROR, state.errorMessage)
                assertFalse(state.isLoading)
            }
        }

    @Test
    fun `executeLoginUseCase when is OperationResult_Error GenericException`() =
        scope.runTest {
            // given
            val flowResponse = flow {
                emit(
                    OperationResult.Error(
                        Exception("unhandled error")
                    )
                )
            }
            coEvery { useCase.invoke(any(), any()) } coAnswers {
                flowResponse
            }

            coEvery {
                resourceManager.getString(Res.string.error_unhandled)
            } returns UNHANDLED_ERROR_MESSAGE

            // when
            viewModel.apply {
                updatePassword(VALID_PASSWORD)
                updateEmail(VALID_EMAIL)
                executeLoginUseCase()
            }
            // then
            viewModel.apply {
                advanceUntilIdle()
                assertNotNull(state.errorMessage)
                assertEquals(
                    UNHANDLED_ERROR_MESSAGE,
                    state.errorMessage
                )
                assertFalse(state.isLoading)
            }
        }
}
