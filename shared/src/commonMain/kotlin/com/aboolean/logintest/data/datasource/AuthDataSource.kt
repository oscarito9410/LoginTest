package com.aboolean.logintest.data.datasource

import com.aboolean.logintest.data.model.AuthRequest
import com.aboolean.logintest.data.model.AuthResponse
import com.aboolean.logintest.foundation.OperationResult

interface AuthDataSource {
    suspend fun login(request: AuthRequest): OperationResult<AuthResponse>
}
