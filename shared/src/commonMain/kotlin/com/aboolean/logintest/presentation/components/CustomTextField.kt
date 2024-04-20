package com.aboolean.logintest.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomTextField(
    initialValue: String,
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit,
    isInvalid: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    focusRequester: FocusRequester? = null
) {
    var text by remember { mutableStateOf(initialValue) }
    var isError by remember { mutableStateOf(isInvalid) }

    LaunchedEffect(isInvalid) {
        isError = isInvalid
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { focusRequester?.requestFocus() }
        ),
        modifier = modifier,
        label = { Text(label) },
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}
