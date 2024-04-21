package com.aboolean.logintest.data.datasource

import com.aboolean.logintest.MockData.INVALID_EMAIL
import com.aboolean.logintest.MockData.INVALID_PASSWORD
import com.aboolean.logintest.MockData.VALID_ACCESS_TOKEN
import com.aboolean.logintest.MockData.VALID_EMAIL
import com.aboolean.logintest.MockData.VALID_PASSWORD
import com.aboolean.logintest.MockData.VALID_USER_NAME
import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.data.model.AuthRequest
import com.aboolean.logintest.foundation.OperationResult
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(
    ExperimentalCoroutinesApi::class
)
class AuthDataSourceTest {

    // suit
    private lateinit var dataSource: AuthDataSource

    // dispatchers
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        dataSource = AuthDataSourceImpl()
    }

    @Test
    fun `execute login when valid credentials OperationResult_Success`() = scope.runTest {
        // given
        val request = AuthRequest(
            VALID_EMAIL,
            VALID_PASSWORD
        )
        // when
        val result = dataSource.login(request)

        // then
        assertTrue(result is OperationResult.Success)
        with(result.data) {
            assertEquals(VALID_EMAIL, email)
            assertEquals(VALID_USER_NAME, userName)
            assertEquals(VALID_ACCESS_TOKEN, accessToken)
        }
    }
    @Test
    fun `execute login when invalid credentials OperationResult_Error`() = scope.runTest {
        // given
        val request = AuthRequest(
            INVALID_EMAIL,
            INVALID_PASSWORD
        )
        // when
        val result = dataSource.login(request)

        // then
        assertTrue(result is OperationResult.Error)
        assertTrue(result.exception is InvalidCredentialException)
    }

}