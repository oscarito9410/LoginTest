package com.aboolean.logintest.data.mapper

import com.aboolean.logintest.data.model.AuthResponse
import com.aboolean.logintest.domain.entities.UserEntity

/**
 * Converts an instance of [AuthResponse] to a [UserEntity] object.
 *
 * @return The converted [UserEntity] object.
 */
fun AuthResponse.toDomain(): UserEntity =
    UserEntity(
        isAuthenticated,
        userName,
        email,
        accessToken
    )
