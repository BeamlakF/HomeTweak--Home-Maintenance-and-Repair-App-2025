package com.example.myapplication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _rememberMe = mutableStateOf(true)
    val rememberMe: State<Boolean> = _rememberMe

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onRememberMeChange(isChecked: Boolean) {
        _rememberMe.value = isChecked
    }

    fun login() {
        // Perform login logic here, like making a network request
    }
}
