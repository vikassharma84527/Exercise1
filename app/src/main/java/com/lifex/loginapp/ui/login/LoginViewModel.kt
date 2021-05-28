package com.lifex.loginapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lifex.loginapp.R
import com.lifex.loginapp.data.LoginRepository
import com.lifex.loginapp.data.model.LoggedInUserData
import com.lifex.loginapp.ui.data_class.LoginFormState
import com.lifex.loginapp.ui.data_class.LoginResult
import com.lifex.loginapp.utils.isPasswordValid
import com.lifex.loginapp.utils.isUserNameValid

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm


    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String, confirmPassword: String) {
        //Validating the user

        val isUserValid = validateUser(username, password, confirmPassword)
        _loginForm.value = isUserValid

        if (isUserValid.isDataValid) {
            /**Returning success back to main class i.e [LoginActivity]*/
            loginRepository.login(username, password)
            _loginResult.value = LoginResult(success = LoggedInUserData(displayName = username))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }


    fun loginDataChanged(username: String, password: String, confirmPassword: String) {
        _loginForm.value = validateUser(username, password, confirmPassword)
    }

    private fun validateUser(
        username: String,
        password: String,
        confirmPassword: String
    ): LoginFormState {
        return if (!isUserNameValid(username)) {
            LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            LoginFormState(passwordError = R.string.invalid_password)
        } else if (confirmPassword != password) {
            LoginFormState(confirmPasswordError = R.string.invalid_confirm_password)
        } else {
            LoginFormState(isDataValid = true)
        }
    }


}