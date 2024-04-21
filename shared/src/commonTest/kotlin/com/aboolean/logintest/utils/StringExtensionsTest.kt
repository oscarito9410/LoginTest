package com.aboolean.logintest.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringExtensionsTest {

    @Test
    fun isEmailInvalid_returns_true_when_meets_criteria() {
        // given - when
        assertTrue("invalid_email".isEmailInvalid())
    }

    @Test
    fun isEmailInvalid_returns_true_when_doesnt_meet_criteria() {
        // given - when
        assertFalse("oscar121094@gmail.com".isEmailInvalid())
    }

    @Test
    fun isPasswordInvalid_returns_true_when_meets_criteria() {
        // given - when
        assertFalse("pass1234$".isInValidPassword())
    }

    @Test
    fun isPasswordInvalid_returns_false_when_doesnt_meet_criteria() {
        // given - when
        assertTrue("pass".isInValidPassword())
    }
}
