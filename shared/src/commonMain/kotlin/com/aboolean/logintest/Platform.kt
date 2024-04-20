package com.aboolean.logintest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
