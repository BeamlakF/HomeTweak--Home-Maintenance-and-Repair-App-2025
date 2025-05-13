package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Category
import com.example.myapplication.data.model.Provider
import com.example.myapplication.data.repository.CategoryRepository
import com.example.myapplication.data.repository.ProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val profession: String = "",
    val serviceType: String = "",
    val certificate: String = "",
    val selectedCategory: Category? = null
)

class FinishSignUpViewModel(
    private val categoryRepository: CategoryRepository,
    private val providerRepository: ProviderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    fun getCategories() {
        viewModelScope.launch {
            try {
                val categoriesList = categoryRepository.getCategories()
                _categories.value = categoriesList
            } catch (_: Exception) {
                // Log or handle error
            }
        }
    }

    fun onCategorySelected(category: Category) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            serviceType = ""
        )
    }

    fun onServiceTypeSelected(value: String) {
        _uiState.value = _uiState.value.copy(serviceType = value)
    }

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
    }

    fun onPhoneChange(value: String) {
        _uiState.value = _uiState.value.copy(phone = value)
    }

    fun onProfessionChange(value: String) {
        _uiState.value = _uiState.value.copy(profession = value)
    }

    fun onCertificateChange(value: String) {
        _uiState.value = _uiState.value.copy(certificate = value)
    }

    fun createProvider() {
        val state = uiState.value
        val selectedService = state.selectedCategory?.services?.find {
            it.displayName == state.serviceType
        } ?: return

        val provider = Provider(
            id = 0,
            name = state.name,
            categoryId = state.selectedCategory.id,
            certificate = state.certificate,
            location = "",
            imageUrl = "",
            phoneNumber = state.phone,
            hourlyRate = "",
            yearsOfExperience = 0,
            serviceType = selectedService.toString()
        )

        viewModelScope.launch {
            try {
                providerRepository.createProvider(provider)
                // Success: navigate or update UI
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
