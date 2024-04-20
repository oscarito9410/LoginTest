package com.aboolean.logintest.data.model

data class AuthResponse(
    val isAuthenticated: Boolean,
    val userName: String,
    val email: String,
    val accessToken: String
)
