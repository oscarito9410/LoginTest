package com.aboolean.logintest.data.repository

import com.aboolean.logintest.data.datasource.AuthDataSource
import com.aboolean.logintest.data.mapper.mapToDomain
import com.aboolean.logintest.data.mapper.toDomain
import com.aboolean.logintest.data.model.AuthRequest
import com.aboolean.logintest.domain.entities.UserEntity
import com.aboolean.logintest.domain.repository.AuthRepository
import com.aboolean.logintest.foundation.OperationResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun doLogin(
        email: String,
        password: String
    ): Flow<OperationResult<UserEntity>> = flow {
        val request = AuthRequest(email, password)
        val result = authDataSource.login(request)
        emit(result)
    }.map { dataResponse ->
        dataResponse.mapToDomain {
            it.toDomain()
        }
    }.flowOn(dispatcher)
}
