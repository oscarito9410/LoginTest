package com.aboolean.logintest.presentation.screens.login

data class LoginViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isInvalidEmail: Boolean = false,
    val isInvalidPassword: Boolean = false,
    val isUserAuthenticated: Boolean = false
)
