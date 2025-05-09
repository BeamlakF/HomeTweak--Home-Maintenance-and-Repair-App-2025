package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DashboardRepo
import com.example.myapplication.model.Dashboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class DashboardViewModel(private val dashboardRepo: DashboardRepo) : ViewModel() {

    private val _profile = MutableStateFlow<Dashboard.UserProfile?>(null)
    val profile: StateFlow<Dashboard.UserProfile?> = _profile

    private val _bookingSummary = MutableStateFlow<Dashboard.BookingSummary?>(null)
    val bookingSummary: StateFlow<Dashboard.BookingSummary?> = _bookingSummary

    private val _currentTab = MutableStateFlow(Dashboard.BookingStatus.ACTIVE)
    val currentTab: StateFlow<Dashboard.BookingStatus> = _currentTab

    private val _pendingBookings = MutableStateFlow<List<Dashboard.Booked>>(emptyList())
    val pendingBookings: StateFlow<List<Dashboard.Booked>> = _pendingBookings

    private val _activeBookings = MutableStateFlow<List<Dashboard.Booked>>(emptyList())
    val activeBookings: StateFlow<List<Dashboard.Booked>> = _activeBookings

    private val _completedBookings = MutableStateFlow<List<Dashboard.Booked>>(emptyList())
    val completedBookings: StateFlow<List<Dashboard.Booked>> = _completedBookings

    private val _bookingActionState = MutableStateFlow<DashboardBookingActionState?>(null)
    val bookingActionState: StateFlow<DashboardBookingActionState?> = _bookingActionState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _expandedBookings = MutableStateFlow<Set<String>>(emptySet())
    val expandedBookings: StateFlow<Set<String>> = _expandedBookings

    val displayedBookings: StateFlow<List<Dashboard.Booked>> = _currentTab.flatMapLatest { status ->
        when (status) {
            Dashboard.BookingStatus.PENDING -> _pendingBookings
            Dashboard.BookingStatus.ACTIVE -> _activeBookings
            Dashboard.BookingStatus.COMPLETED -> _completedBookings
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                launch {
                    _profile.update { dashboardRepo.getServiceProviderProfile() }
                }
                launch {
                    _bookingSummary.update { dashboardRepo.getBookingSummary() }
                }

                _pendingBookings.update { dashboardRepo.getPendingBookings() }
                _activeBookings.update { dashboardRepo.getActiveBookings() }
                _completedBookings.update { dashboardRepo.getCompletedBookings() }
                _isLoading.update { false }
            } catch (e: Exception) {
                _errorMessage.update { "Failed to load data: ${e.localizedMessage}" }
                _isLoading.update { false }
            }
        }
    }

    fun onTabSelected(status: Dashboard.BookingStatus) {
        _currentTab.update { status }
    }

    fun confirmBooking(bookingId: String) {
        viewModelScope.launch {
            try {
                val success = dashboardRepo.confirmBooking(bookingId)
                if (success) {
                    val confirmedBooking = _pendingBookings.value.find { it.id == bookingId }
                    confirmedBooking?.let {
                        _pendingBookings.update { it.filter { booking -> booking.id != bookingId } }
                        _activeBookings.update { it + confirmedBooking.copy(status = Dashboard.BookingStatus.ACTIVE) }
                        _bookingActionState.update { DashboardBookingActionState.BookingConfirmed(bookingId) }
                    }
                } else {
                    _errorMessage.update { "Failed to confirm booking." }
                    _bookingActionState.update { DashboardBookingActionState.Error("Failed to confirm booking.") }
                }
            } catch (e: Exception) {
                _errorMessage.update { "Error confirming booking: ${e.localizedMessage}" }
                _bookingActionState.update { DashboardBookingActionState.Error("Error confirming booking: ${e.localizedMessage}") }
            } finally {
                _bookingActionState.update { null }
            }
        }
    }

    fun declineBooking(bookingId: String) {
        viewModelScope.launch {
            try {
                val success = dashboardRepo.declineBooking(bookingId)
                if (success) {
                    _pendingBookings.update { it.filter { booking -> booking.id != bookingId } }
                    _bookingActionState.update { DashboardBookingActionState.BookingDeclined(bookingId) }
                } else {
                    _errorMessage.update { "Failed to decline booking." }
                    _bookingActionState.update { DashboardBookingActionState.Error("Failed to decline booking.") }
                }
            } catch (e: Exception) {
                _errorMessage.update { "Error declining booking: ${e.localizedMessage}" }
                _bookingActionState.update { DashboardBookingActionState.Error("Error declining booking: ${e.localizedMessage}") }
            } finally {
                _bookingActionState.update { null }
            }
        }
    }

    fun toggleBookingDetails(bookingId: String) {
        _expandedBookings.update { currentExpanded ->
            if (currentExpanded.contains(bookingId)) {
                currentExpanded - bookingId
            } else {
                currentExpanded + bookingId
            }
        }
    }

    companion object {
        fun provideFactory(
            dashboardRepo: DashboardRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                    return DashboardViewModel(dashboardRepo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

sealed class DashboardBookingActionState {
    data class BookingConfirmed(val bookingId: String) : DashboardBookingActionState()
    data class BookingDeclined(val bookingId: String) : DashboardBookingActionState()
    data class Error(val message: String) : DashboardBookingActionState()
}
