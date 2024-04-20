package com.aboolean.logintest.domain.repository

import com.aboolean.logintest.domain.entities.UserEntity
import com.aboolean.logintest.foundation.OperationResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling authentication operations.
 */
interface AuthRepository {
    /**
     * Performs login operation with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A flow that emits the result of the login operation.
     */
    suspend fun doLogin(
        email: String,
        password: String
    ): Flow<OperationResult<UserEntity>>
}
