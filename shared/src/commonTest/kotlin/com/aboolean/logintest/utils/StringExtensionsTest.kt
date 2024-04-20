package com.aboolean.logintest.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringExtensionsTest {

    @Test
    fun `isEmailInvalid returns true when meets criteria`() {
        // given - when
        assertTrue("invalid_email".isEmailInvalid())
    }

    @Test
    fun `isEmailInvalid returns false when doesn't criteria`() {
        // given - when
        assertFalse("oscar121094@gmail.com".isEmailInvalid())
    }

    @Test
    fun `isPasswordInvalid returns true when meets criteria`() {
        // given - when
        assertFalse("pass1234$".isInValidPassword())
    }

    @Test
    fun `isPasswordInvalid returns false when doesn't criteria`() {
        // given - when
        assertTrue("pass".isInValidPassword())
    }
}
