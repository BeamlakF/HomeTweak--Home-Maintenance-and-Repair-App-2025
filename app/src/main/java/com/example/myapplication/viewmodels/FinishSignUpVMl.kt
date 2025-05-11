package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Provider
import com.example.myapplication.data.repository.ProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val profession: String = "",
    val service1: String = "",
    val service2: String = "",
    val certification1: String = "",
    val license: String = "",
    val basicExpanded: Boolean = true,
    val professionalExpanded: Boolean = false,
    val servicesExpanded: Boolean = false,
    val certificationsExpanded: Boolean = false
)

class FinishSignUpViewModel(private val providerRepository: ProviderRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun updateField(field: String, value: String) {
        _uiState.update { current ->
            when (field) {
                "name" -> current.copy(name = value)
                "email" -> current.copy(email = value)
                "phone" -> current.copy(phone = value)
                "profession" -> current.copy(profession = value)
                "service1" -> current.copy(service1 = value)
                "service2" -> current.copy(service2 = value)
                "certification1" -> current.copy(certification1 = value)
                "license" -> current.copy(license = value)
                else -> current
            }
        }
    }

    fun toggleSection(section: String) {
        _uiState.update { current ->
            when (section) {
                "basic" -> current.copy(basicExpanded = !current.basicExpanded)
                "professional" -> current.copy(professionalExpanded = !current.professionalExpanded)
                "services" -> current.copy(servicesExpanded = !current.servicesExpanded)
                "certifications" -> current.copy(certificationsExpanded = !current.certificationsExpanded)
                else -> current
            }
        }
    }

    fun submit() {
        val provider = Provider(
            id = 0, // This can be assigned once the provider is created in the backend
            userId = 1, // You should replace this with the actual user ID
            categoryId = 1, // Set appropriate category
            certificate = _uiState.value.certification1,
            location = "", // You can set the location if you collect this data
            imageUrl = "", // Add image URL logic if needed
            phoneNumber = _uiState.value.phone,
            hourlyRate = "", // Set hourly rate if applicable
            yearsOfExperience = 0, // Add experience years if needed
            rating = 0f, // Rating logic if required
            serviceDescription = _uiState.value.service1
        )
        saveProvider(provider)
    }

    private fun saveProvider(provider: Provider) {
        viewModelScope.launch {
            providerRepository.createProvider(provider) { createdProvider ->
                if (createdProvider != null) {
                    // Handle success, maybe update UI state or navigate
                } else {
                    // Handle error, show message to user
                }
            }
        }
    }

    fun logout() {
        // TODO: Implement logout logic
    }
}
