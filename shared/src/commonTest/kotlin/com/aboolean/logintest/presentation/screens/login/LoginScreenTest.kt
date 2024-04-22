package com.aboolean.logintest.presentation.screens.login

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import com.aboolean.logintest.SharedMockData.INVALID_CREDENTIALS_ERROR
import com.aboolean.logintest.SharedMockData.UNHANDLED_ERROR_MESSAGE
import com.aboolean.logintest.SharedMockData.VALID_EMAIL
import com.aboolean.logintest.SharedMockData.VALID_PASSWORD
import com.aboolean.logintest.SharedMockData.VALID_USER_NAME
import com.aboolean.logintest.data.exception.InvalidCredentialException
import com.aboolean.logintest.domain.entities.UserEntity
import com.aboolean.logintest.domain.repository.AuthRepository
import com.aboolean.logintest.domain.usecase.DoLoginUseCase
import com.aboolean.logintest.foundation.OperationResult
import com.aboolean.logintest.presentation.components.alertdialog.AlertType
import com.aboolean.logintest.presentation.manager.ResourceManagerImpl
import com.aboolean.logintest.presentation.screens.login.TestTags.EMAIL_TEXT_FIELD
import com.aboolean.logintest.presentation.screens.login.TestTags.LOGIN_BUTTON
import com.aboolean.logintest.presentation.screens.login.TestTags.PASSWORD_TEXT_FIELD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.test.Ignore
import kotlin.test.Test

/**
 * This test class will fail, if you use gutter icons in Android Studio
 * see https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html#write-and-run-common-tests
 * use ./gradlew shared:connectedAndroidTest and remove the ignore annotation
 */

@Ignore
@OptIn(ExperimentalTestApi::class)
class LoginScreenTest {

    @Test
    fun login_screen_when_credentials_are_valid_shows_success_dialog() = runComposeUiTest {
        // given
        val user = UserEntity(
            userName = VALID_USER_NAME,
            email = VALID_EMAIL,
            isAuthenticated = true,
            accessToken = "token"
        )
        setContent {
            LoginScreen(
                fixtureLoginViewModel(
                    willRespond = OperationResult.Success(
                        data = user
                    )
                )
            )
        }
        // when
        onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput(VALID_EMAIL)
        onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput(VALID_PASSWORD)
        onNodeWithTag(LOGIN_BUTTON).performClick()

        // then verify info dialog is displayed
        onNodeWithTag(AlertType.INFO.name).assertIsDisplayed()
    }

    @Test
    fun login_screen_when_credentials_are_invalid_shows_error_dialog() = runComposeUiTest {
        // given
        setContent {
            LoginScreen(
                fixtureLoginViewModel(
                    willRespond = OperationResult.Error(InvalidCredentialException())
                )
            )
        }
        // when
        onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput(VALID_EMAIL)
        onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput("not valid password")
        onNodeWithTag(LOGIN_BUTTON).performClick()

        // then verify error dialog is displayed
        onNodeWithTag(AlertType.ERROR.name).assertIsDisplayed()
        onNodeWithText(INVALID_CREDENTIALS_ERROR).assertIsDisplayed()
    }

    @Test
    fun login_screen_when_credentials_unhandled_error_shows_generic_error_dialog() = runComposeUiTest {
        // given
        setContent {
            LoginScreen(
                fixtureLoginViewModel(
                    willRespond = OperationResult.Error(Exception("Unhandled error"))
                )
            )
        }
        // when
        onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput(VALID_EMAIL)
        onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput("not valid password")
        onNodeWithTag(LOGIN_BUTTON).performClick()

        // then verify error dialog is displayed
        onNodeWithTag(AlertType.ERROR.name).assertIsDisplayed()
        onNodeWithText(UNHANDLED_ERROR_MESSAGE).assertIsDisplayed()
    }

    private fun fixtureLoginViewModel(
        willRespond: OperationResult<UserEntity>
    ): LoginViewModel = LoginViewModel(
        DoLoginUseCase(
            authRepository = object : AuthRepository {
                override suspend fun doLogin(
                    email: String,
                    password: String
                ): Flow<OperationResult<UserEntity>> = flowOf(willRespond)
            }
        ),
        ResourceManagerImpl()
    )
}
