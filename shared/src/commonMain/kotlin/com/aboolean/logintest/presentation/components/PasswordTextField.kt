package com.aboolean.logintest.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    initialValue: String,
    onValueChange: (String) -> Unit,
    onDonePressed: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    isInvalid: Boolean,
    errorMessage: String,
) {
    val passwordVisibility = remember { mutableStateOf(true) }
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
        label = { Text(text = label) },
        modifier = modifier,
        visualTransformation = if (passwordVisibility.value)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDonePressed()
            }
        ),
        trailingIcon = {
            Icon(
                imageVector = if (passwordVisibility.value) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                },
                contentDescription = if (passwordVisibility.value) {
                    Icons.Filled.VisibilityOff.name
                } else {
                    Icons.Filled.Visibility.name
                },
                modifier = Modifier
                    .clickable { passwordVisibility.value = !passwordVisibility.value }
                    .padding(8.dp)
            )
        },
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
