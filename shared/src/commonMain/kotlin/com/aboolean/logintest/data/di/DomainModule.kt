package com.aboolean.logintest.data.di

import com.aboolean.logintest.data.datasource.AuthDataSource
import com.aboolean.logintest.data.datasource.AuthDataSourceImpl
import com.aboolean.logintest.data.repository.AuthRepositoryImpl
import com.aboolean.logintest.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

fun dataModule(): Module = module {
    single<CoroutineDispatcher> {
        Dispatchers.IO
    }
    factory<AuthDataSource> { AuthDataSourceImpl() }
    factory<AuthRepository> {
        AuthRepositoryImpl(
            authDataSource = get(),
            dispatcher = get()
        )
    }
}
