package com.aboolean.logintest.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.aboolean.logintest.presentation.components.CustomTextField
import com.aboolean.logintest.presentation.components.FullScreenLoader
import com.aboolean.logintest.presentation.components.PasswordTextField
import com.aboolean.logintest.presentation.components.alertdialog.AlertType
import com.aboolean.logintest.presentation.components.alertdialog.InfoAlertDialog
import com.aboolean.logintest.presentation.screens.login.TestTags.EMAIL_TEXT_FIELD
import com.aboolean.logintest.presentation.screens.login.TestTags.LOGIN_BUTTON
import com.aboolean.logintest.presentation.screens.login.TestTags.PASSWORD_TEXT_FIELD
import com.aboolean.logintest.presentation.theme.AppTheme
import com.aboolean.logintest.presentation.theme.Dimens.ImageLoginSize
import com.aboolean.logintest.presentation.theme.Dimens.LargeSpace
import com.aboolean.logintest.presentation.theme.Dimens.SmallSpace
import com.aboolean.logintest.presentation.theme.Dimens.TextFieldHorizontalPadding
import com.aboolean.logintest.presentation.theme.Dimens.TextFieldPadding
import com.aboolean.logintest.presentation.theme.TextSize
import logintest.shared.generated.resources.Res
import logintest.shared.generated.resources.error_invalid_email_account
import logintest.shared.generated.resources.error_invalid_password
import logintest.shared.generated.resources.hint_email
import logintest.shared.generated.resources.hint_password
import logintest.shared.generated.resources.ic_login_welcome
import logintest.shared.generated.resources.message_welcome_user
import logintest.shared.generated.resources.text_login
import logintest.shared.generated.resources.text_welcome
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinInject()) = viewModel.run {
    AppTheme {
        LoginScreenContent(
            state,
            email = email,
            password = password,
            isLoginActionEnabled = loginActionEnabled,
            emailChanged = ::updateEmail,
            passwordChanged = ::updatePassword,
            onLoginPressed = ::onLoginPressed,
            onResolveError = ::resetLoginState
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
fun LoginScreenContent(
    state: LoginViewState,
    email: String,
    password: String,
    isLoginActionEnabled: Boolean,
    onResolveError: () -> Unit = {},
    emailChanged: (String) -> Unit = {},
    passwordChanged: (String) -> Unit = {},
    onLoginPressed: () -> Unit = {},
) {
    val (focusRequester) = FocusRequester.createRefs()

    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.ic_login_welcome),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
                .size(ImageLoginSize)
        )
        Column(
            modifier = Modifier.padding(horizontal = LargeSpace),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.text_welcome),
                fontSize = TextSize.Large,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = LargeSpace)
            )

            CustomTextField(
                initialValue = email,
                onValueChange = emailChanged,
                label = stringResource(Res.string.hint_email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = TextFieldHorizontalPadding,
                        vertical = TextFieldPadding
                    ).testTag(EMAIL_TEXT_FIELD),
                isInvalid = state.isInvalidEmail,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                focusRequester = focusRequester,
                errorMessage = stringResource(Res.string.error_invalid_email_account)
            )

            PasswordTextField(
                label = stringResource(Res.string.hint_password),
                initialValue = password,
                onValueChange = passwordChanged,
                onDonePressed = onLoginPressed,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(
                        horizontal = TextFieldHorizontalPadding,
                        vertical = TextFieldPadding
                    ).testTag(PASSWORD_TEXT_FIELD),
                isInvalid = state.isInvalidPassword,
                errorMessage = stringResource(Res.string.error_invalid_password)
            )

            Spacer(modifier = Modifier.height(SmallSpace))

            Button(
                onClick = onLoginPressed,
                enabled = isLoginActionEnabled,
                modifier = Modifier.fillMaxWidth().padding(
                    horizontal = TextFieldHorizontalPadding
                ).testTag(LOGIN_BUTTON)
            ) {
                Text(text = stringResource(Res.string.text_login))
            }
        }
    }
    when {
        state.isLoading -> FullScreenLoader()
        state.errorMessage != null -> InfoAlertDialog(
            text = state.errorMessage,
            type = AlertType.ERROR,
            modifier = Modifier.fillMaxWidth().testTag(
                AlertType.ERROR.name
            ),
            onConfirm = onResolveError
        )

        state.userResult != null -> InfoAlertDialog(
            text = stringResource(
                Res.string.message_welcome_user,
                state.userResult.userName
            ),
            type = AlertType.INFO,
            modifier = Modifier.fillMaxWidth().testTag(
                AlertType.INFO.name
            ),
            onConfirm = onResolveError
        )
    }
}
