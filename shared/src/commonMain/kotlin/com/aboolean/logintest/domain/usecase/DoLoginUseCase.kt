package com.aboolean.logintest.domain.usecase

import com.aboolean.logintest.domain.entities.UserEntity
import com.aboolean.logintest.domain.repository.AuthRepository
import com.aboolean.logintest.foundation.OperationResult
import kotlinx.coroutines.flow.Flow

/**
 * Use case for performing login.
 *
 * @param authRepository The repository for authentication-related operations.
 */
class DoLoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<OperationResult<UserEntity>> = authRepository.doLogin(email, password)
}
