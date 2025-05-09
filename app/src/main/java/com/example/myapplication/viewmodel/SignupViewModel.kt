package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.SignUpRequest
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val role: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    private val repository = AuthRepository()

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
            _uiState.value = state.copy(errorMessage = "Please fill all fields.")
            return
        }

        _uiState.value = state.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val request = SignUpRequest(
                email = state.email,
                password = state.password,
                role = state.role
            )

            val result = repository.signUp(request)

            result.onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = response.message
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.localizedMessage ?: "Signup failed"
                )
            }
        }
    }
}
