package com.aboolean.logintest.domain.di

import com.aboolean.logintest.domain.usecase.DoLoginUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun domainModule(): Module = module {
    single<DoLoginUseCase> { DoLoginUseCase(authRepository = get()) }
}
