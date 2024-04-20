package com.aboolean.logintest.foundation

import kotlinx.coroutines.CoroutineScope

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class ViewModel() {

    /**
     * [CoroutineScope] tied to this [ViewModel].
     * This scope will be canceled when ViewModel will be cleared, i.e [ViewModel.onCleared] is called.
     *
     * ### On Android
     * - This scope is `androidx.lifecycle.viewModelScope` from AndroidX.
     *
     * - It is created lazily, and can be accessed from any thread (it is thread-safe).
     *
     * ### Other platforms
     * - It is bound to
     * [Dispatchers.IO][kotlinx.coroutines.Dispatchers.IO].
     */
    val viewModelScope: CoroutineScope

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    protected open fun onCleared()
}
