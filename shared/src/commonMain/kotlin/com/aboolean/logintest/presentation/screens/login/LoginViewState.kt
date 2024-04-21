package com.aboolean.logintest.presentation.screens.login

import com.aboolean.logintest.domain.entities.UserEntity

data class LoginViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isInvalidEmail: Boolean = false,
    val isInvalidPassword: Boolean = false,
    val userResult: UserEntity? = null,
)
