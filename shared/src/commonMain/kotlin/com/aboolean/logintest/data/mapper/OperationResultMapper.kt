package com.aboolean.logintest.data.mapper

import com.aboolean.logintest.foundation.OperationResult

/**
 * Maps the result of [OperationResult<A>] to [OperationResult<B>].
 *
 * @param mapper The mapping function to convert [A] to [B].
 * @return The mapped result.
 */
fun <A, B> OperationResult<A>.mapToDomain(mapper: (A) -> B): OperationResult<B> = when (this) {
    is OperationResult.Success -> OperationResult.Success(mapper(data))
    is OperationResult.Error -> OperationResult.Error(exception)
}
