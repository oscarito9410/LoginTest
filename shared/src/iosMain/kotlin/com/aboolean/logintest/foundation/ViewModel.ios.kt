package com.aboolean.logintest.foundation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class ViewModel {

    actual val viewModelScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.IO)

    protected actual open fun onCleared() {
        viewModelScope.cancel()
    }
}
