package com.aboolean.logintest.domain.entities

data class UserEntity(
    val isAuthenticated: Boolean,
    val userName: String,
    val email: String,
    val accessToken: String
)
