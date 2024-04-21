package com.aboolean.logintest.data.mapper

import com.aboolean.logintest.data.model.AuthResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AuthDataToDomainTest {

    @Test
    fun `toDomain converts AuthResponse to UserEntity correctly`() {
        // given
        val authResponse = AuthResponse(
            isAuthenticated = true,
            userName = "JohnDoe",
            email = "johndoe@example.com",
            accessToken = "access-token-123"
        )

        // when
        val userEntity = authResponse.toDomain()

        // then
        assertNotNull(userEntity)
        assertEquals(
            authResponse.isAuthenticated,
            userEntity.isAuthenticated
        )
        assertEquals(
            authResponse.userName,
            userEntity.userName
        )
        assertEquals(
            authResponse.email,
            userEntity.email
        )
        assertEquals(
            authResponse.accessToken,
            userEntity.accessToken
        )
    }
}
