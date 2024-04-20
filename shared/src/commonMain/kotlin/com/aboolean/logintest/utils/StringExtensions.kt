package com.aboolean.logintest.utils

private const val MIN_LENGTH_PASSWORD = 5

private fun String.isEmailValid(): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    return matches(emailRegex)
}

fun String.isEmailInvalid() = this.isEmailValid().not()

fun String.isInValidPassword() = this.isNotBlank() && this.length < MIN_LENGTH_PASSWORD
