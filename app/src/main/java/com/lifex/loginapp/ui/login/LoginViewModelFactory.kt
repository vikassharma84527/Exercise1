package com.lifex.loginapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifex.loginapp.data.LoginRepository

/**
 * ViewModel provider factory to instantiate [LoginViewModel].
 * We can create a baseViewModel To make it usable for all viewModels
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository = LoginRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}