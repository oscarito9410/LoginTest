package com.aboolean.logintest.android

import android.app.Application
import com.aboolean.logintest.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class LoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(level = Level.NONE)
            androidContext(androidContext = this@LoginApplication)
        }
    }
}
