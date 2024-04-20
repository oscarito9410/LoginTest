package com.aboolean.logintest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aboolean.logintest.presentation.theme.Dimens.AlphaLoadingIndicator
import com.aboolean.logintest.presentation.theme.Dimens.LoadingIndicatorSize

@Composable
fun FullScreenLoader() = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(
            Color.Black.copy(
                alpha = AlphaLoadingIndicator
            )
        ),
    contentAlignment = Alignment.Center
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(
                LoadingIndicatorSize
            ),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
