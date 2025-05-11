package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProviderProfileVM : ViewModel() {
        private val _user = MutableStateFlow(User(...))
        val user: StateFlow<User> = _user.asStateFlow()

        fun updateField(fieldName: String, value: String) {
            _user.update { current ->
                when (fieldName) {
                    "fullName" -> current.copy(fullName = value)
                    "email" -> current.copy(email = value)
                    "phoneNumber" -> current.copy(phoneNumber = value)
                    "location" -> current.copy(location = value)
                    "externalLinks" -> current.copy(externalLinks = value)
                    "category" -> current.copy(category = value)
                    "experienceLevel" -> current.copy(experienceLevel = value)
                    "hourlyRate" -> current.copy(hourlyRate = value)
                    "services" -> current.copy(services = value)
                    "certifications" -> current.copy(certifications = value)
                    else -> current
                }
            }
        }

        fun updateUser(user: User) {
            _user.value = user
            // TODO: Send update to your backend
        }

        fun logout() {
            // TODO: Clear user session, navigate to login, etc.
        }

        fun deleteAccount() {
            // TODO: Call API to delete user account
            // Then clear local state or redirect user
        }
    }
}