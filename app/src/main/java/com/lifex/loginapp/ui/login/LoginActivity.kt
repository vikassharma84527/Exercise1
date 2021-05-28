package com.lifex.loginapp.ui.login

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.lifex.loginapp.R
import com.lifex.loginapp.data.model.LoggedInUserData
import com.lifex.loginapp.utils.afterTextChanged
import com.lifex.loginapp.utils.clearError
import com.lifex.loginapp.utils.showLoginSuccessDialog

class LoginActivity : AppCompatActivity() {


    companion object {
/**
        Variable if we want to notify errors on real time
*/
        const val isRealTimeUpdate = false
    }

    //Variables initialized
    private lateinit var loginViewModel: LoginViewModel


    private val username by lazy { findViewById<EditText>(R.id.username) }
    private val password by lazy { findViewById<EditText>(R.id.password) }
    private val confirmPassword by lazy { findViewById<EditText>(R.id.confirmPassword) }
    private val usernameLayout by lazy { findViewById<TextInputLayout>(R.id.usernameLayout) }
    private val confirmPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.confirmPasswordLayout) }
    private val passwordLayout by lazy { findViewById<TextInputLayout>(R.id.passwordLayout) }
    private val login by lazy { findViewById<Button>(R.id.login) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Check for portrait mode according to screen width
        if(resources.getBoolean(R.bool.portrait_only)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observers()
        listeners()
    }

    private fun listeners() {
        username.afterTextChanged {
            loginDataChanged()
        }

        password.apply {
            afterTextChanged {
                loginDataChanged()
            }
        }

        confirmPassword.apply {
            afterTextChanged {
                loginDataChanged()
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> loginWithViewModel()
                }
                false
            }
        }

        login.setOnClickListener {
            clearErrors()
            loginWithViewModel()
        }
    }

    private fun observers() {
        //observers from the viewmodel at one place
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            if (loginState.usernameError != null) {
                usernameLayout.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                passwordLayout.error = getString(loginState.passwordError)
            }
            if (loginState.confirmPasswordError != null) {
                confirmPasswordLayout.error = getString(loginState.confirmPasswordError)
            }
        })



        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
        })
    }

    private fun loginDataChanged() {
        //this method is for real time update with viewmodel, in future if we do not need it, we can remove this

        if (isRealTimeUpdate) {
            clearErrors()
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }
    }

    private fun clearErrors() {
        usernameLayout.clearError()
        confirmPasswordLayout.clearError()
        passwordLayout.clearError()
    }

    private fun loginWithViewModel() {
        loginViewModel.login(
            username.text.toString(),
            password.text.toString(),
            confirmPassword.text.toString()
        )
    }

    private fun updateUiWithUser(model: LoggedInUserData) {
        val displayName = model.displayName
        showLoginSuccessDialog(this, displayName)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

