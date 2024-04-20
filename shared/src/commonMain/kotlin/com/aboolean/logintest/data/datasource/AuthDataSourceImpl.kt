package com.aboolean.logintest.data.datasource

import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.data.model.AuthRequest
import com.aboolean.logintest.data.model.AuthResponse
import com.aboolean.logintest.foundation.OperationResult
import kotlinx.coroutines.delay

class AuthDataSourceImpl : AuthDataSource {
    override suspend fun login(request: AuthRequest): OperationResult<AuthResponse> {
        delay(DELAY_SIMULATION_MILLIS)
        val isValidRequest = isValidAuthRequest(request)
        if (!isValidRequest) {
            return (OperationResult.Error(InvalidCredentialException()))
        }
        return OperationResult.Success(buildSuccessAuthResponse())
    }

    private fun buildSuccessAuthResponse(): AuthResponse = AuthResponse(
        isAuthenticated = true,
        userName = VALID_USER_NAME,
        email = VALID_USER_EMAIL,
        accessToken = VALID_ACCESS_TOKEN
    )

    private fun isValidAuthRequest(request: AuthRequest): Boolean =
        request.email == VALID_USER_EMAIL && request.password == VALID_PASSWORD

    private companion object {
        const val DELAY_SIMULATION_MILLIS = 3000L

        // fake valid credentials
        const val VALID_USER_EMAIL = "tester@gmail.com"
        const val VALID_PASSWORD = "bauabapTest"
        const val VALID_USER_NAME = "userName"
        const val VALID_ACCESS_TOKEN = "199348jsh$"
    }
}
