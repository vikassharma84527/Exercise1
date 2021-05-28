package com.lifex.loginapp.ui.data_class

import com.lifex.loginapp.data.model.LoggedInUserData

/**Data to send the result back to main class*/
data class LoginResult(
        val success: LoggedInUserData? = null,
        val error: Int? = null
)