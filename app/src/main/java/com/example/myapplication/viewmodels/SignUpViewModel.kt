package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(
    private val repository: UserRepository
) : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val selectedRole = MutableStateFlow("")
    val expanded = MutableStateFlow(false)

    val roleOptions = listOf("Service Provider", "Customer")

    private val _signUpResult = MutableStateFlow<String>("")
    val signUpResult: StateFlow<String> = _signUpResult

    fun onEmailChange(new: String) { email.value = new }
    fun onPasswordChange(new: String) { password.value = new }
    fun onRoleSelected(role: String) {
        selectedRole.value = role
        expanded.value = false
    }
    fun onExpandedChange(new: Boolean) {
        expanded.value = new
    }

    fun onSignUp() {
        // Make the sign-up API call via the repository
        viewModelScope.launch {
            val response: Response<UserProfile> = repository.signUp(email.value, password.value, selectedRole.value)
            if (response.isSuccessful) {
                _signUpResult.value = "Sign-up successful! Welcome, ${response.body()?.email}"
            } else {
                _signUpResult.value = "Sign-up failed: ${response.errorBody()?.string()}"
            }
        }
    }
}
