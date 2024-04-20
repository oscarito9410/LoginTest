package com.aboolean.logintest.foundation

/**
 * Represents the result of an operation, which can be either a success or an error.
 *
 * @param R The type of the success result.
 * @param T The result data will be returned
 */
sealed class OperationResult<out R> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val exception: Throwable) : OperationResult<Nothing>()
}
