package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val role: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false
)

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onRoleSelected(role: String) {
        _uiState.value = _uiState.value.copy(role = role)
    }

    fun signUp() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank() || state.role.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Please fill out all fields")
            return
        }

        _uiState.value = state.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            // Simulate backend call
            kotlinx.coroutines.delay(1500)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                success = true,
                errorMessage = null
            )
        }
    }
}
