package com.aboolean.logintest.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.domain.usecase.DoLoginUseCase
import com.aboolean.logintest.foundation.OperationResult
import com.aboolean.logintest.foundation.ViewModel
import com.aboolean.logintest.presentation.manager.ResourceManager
import com.aboolean.logintest.utils.isEmailInvalid
import com.aboolean.logintest.utils.isInValidPassword
import kotlinx.coroutines.launch
import logintest.shared.generated.resources.Res
import logintest.shared.generated.resources.error_service_auth
import logintest.shared.generated.resources.error_unhandled
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class LoginViewModel(
    private val doLoginUseCase: DoLoginUseCase,
    private val resourceManager: ResourceManager
) : ViewModel() {

    // region states
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginActionEnabled by mutableStateOf(false)
        private set

    var state by mutableStateOf(LoginViewState())
        private set

    //endregion

    fun onLoginPressed() {
        if (email.isEmailInvalid()) {
            updateState(
                isInvalidEmail = true
            )
        } else if (password.isInValidPassword()) {
            updateState(
                isInvalidPassword = true
            )
        } else {
            executeLoginUseCase()
        }
    }

    fun executeLoginUseCase() = viewModelScope.launch {
        updateState(isLoading = true)
        doLoginUseCase(email, password).collect {
            when (it) {
                is OperationResult.Success -> {
                    val userResult = it.data
                    updateState(
                        isLoading = false,
                        isUserAuthenticated = userResult.isAuthenticated
                    )
                }

                is OperationResult.Error -> {
                    updateState(
                        isLoading = false,
                        errorMessage = getErrorMessageForException(it.exception)
                    )
                }
            }
        }
    }

    private suspend fun getErrorMessageForException(error: Throwable): String =
        if (error is InvalidCredentialException) {
            resourceManager.getString(Res.string.error_service_auth)
        } else {
            resourceManager.getString(Res.string.error_unhandled)
        }

    fun updateEmail(input: String) {
        email = input
        handleTextChange()
    }

    fun updatePassword(input: String) {
        password = input
        handleTextChange()
    }

    fun resetLoginState() {
        updateState(
            isLoading = false,
            errorMessage = null
        )
    }

    private fun handleTextChange() {
        loginActionEnabled = password.isNotEmpty() && email.isNotEmpty()
    }

    private fun updateState(
        isLoading: Boolean = false,
        errorMessage: String? = null,
        isInvalidEmail: Boolean = false,
        isInvalidPassword: Boolean = false,
        isUserAuthenticated: Boolean = false,
    ) {
        state = state.copy(
            isLoading = isLoading,
            isInvalidEmail = isInvalidEmail,
            isInvalidPassword = isInvalidPassword,
            errorMessage = errorMessage,
            isUserAuthenticated = isUserAuthenticated
        )
    }
}
