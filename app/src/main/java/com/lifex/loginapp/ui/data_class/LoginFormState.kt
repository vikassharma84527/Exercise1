package com.lifex.loginapp.ui.data_class

/**
 * Data validation states of the login form.
 */
data class LoginFormState(val usernameError: Int? = null,
                          val passwordError: Int? = null,
                          val confirmPasswordError: Int? = null,
                          val isDataValid: Boolean = false)