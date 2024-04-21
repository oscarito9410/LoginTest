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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aboolean.logintest.presentation.components.CustomTextField
import com.aboolean.logintest.presentation.components.FullScreenLoader
import com.aboolean.logintest.presentation.components.PasswordTextField
import com.aboolean.logintest.presentation.components.alertdialog.AlertType
import com.aboolean.logintest.presentation.components.alertdialog.InfoAlertDialog
import com.aboolean.logintest.presentation.theme.AppTheme
import com.aboolean.logintest.presentation.theme.Dimens.TextFieldHorizontalPadding
import com.aboolean.logintest.presentation.theme.Dimens.TextFieldPadding
import logintest.shared.generated.resources.Res
import logintest.shared.generated.resources.error_invalid_email_account
import logintest.shared.generated.resources.error_invalid_password
import logintest.shared.generated.resources.hint_email
import logintest.shared.generated.resources.hint_password
import logintest.shared.generated.resources.ic_login_welcome
import logintest.shared.generated.resources.text_login
import logintest.shared.generated.resources.text_welcome
import logintest.shared.generated.resources.message_welcome_user
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
                .size(200.dp)
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.text_welcome),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
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
                    ),
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
                    ),
                isInvalid = state.isInvalidPassword,
                errorMessage = stringResource(Res.string.error_invalid_password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onLoginPressed,
                enabled = isLoginActionEnabled,
                modifier = Modifier.fillMaxWidth().padding(
                    horizontal = TextFieldHorizontalPadding
                )
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
            onConfirm = onResolveError
        )
        state.userResult != null -> InfoAlertDialog(
            text = stringResource(
                Res.string.message_welcome_user,
                state.userResult.userName
            ),
            type = AlertType.INFO,
            onConfirm = onResolveError
        )
    }
}
