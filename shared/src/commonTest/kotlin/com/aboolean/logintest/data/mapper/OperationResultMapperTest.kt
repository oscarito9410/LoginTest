package com.aboolean.logintest.data.mapper

import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.foundation.OperationResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class OperationResultTest {

    @Test
    fun `mapToDomain maps success correctly`() {
        // given
        val original = OperationResult.Success(5)
        val expected = OperationResult.Success(10)

        // when
        val result = original.mapToDomain { it * 2 }

        // then
        assertTrue(result is OperationResult.Success)
        assertEquals(expected, result)
        assertEquals(10, result.data)
    }

    @Test
    fun `mapToDomain preserves error`() {
        // given
        val originalError = InvalidCredentialException()
        val original = OperationResult.Error(originalError)

        // when
        val result = original.mapToDomain { it }

        // then
        assertTrue(result is OperationResult.Error)
        assertSame(originalError, result.exception)
    }
}
