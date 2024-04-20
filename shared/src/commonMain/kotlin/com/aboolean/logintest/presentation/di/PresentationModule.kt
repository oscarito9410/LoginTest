package com.aboolean.logintest.presentation.di

import com.aboolean.logintest.presentation.manager.ResourceManager
import com.aboolean.logintest.presentation.manager.ResourceManagerImpl
import com.aboolean.logintest.presentation.screens.login.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun presentationModule(): Module = module {
    single<ResourceManager> { ResourceManagerImpl() }
    singleOf(::LoginViewModel)
}
