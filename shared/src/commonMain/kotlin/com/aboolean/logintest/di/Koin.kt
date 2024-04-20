package com.aboolean.logintest.di

import com.aboolean.logintest.data.di.dataModule
import com.aboolean.logintest.domain.di.domainModule
import com.aboolean.logintest.presentation.di.presentationModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(
            dataModule(),
            domainModule(),
            presentationModule()
        )
    }

// called by IOS
fun KoinApplication.Companion.start(): KoinApplication = initKoin { }
