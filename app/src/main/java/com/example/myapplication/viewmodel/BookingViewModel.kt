package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.BookingRepo
import com.example.myapplication.model.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingViewModel(private val bookingRepository: BookingRepo) : ViewModel() {

    private val _profilePicture = MutableStateFlow<String?>(null)
    val profilePicture: StateFlow<String?> = _profilePicture

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings

    private val _activeBookings = MutableStateFlow<List<Booking>>(emptyList())
    val activeBookings: StateFlow<List<Booking>> = _activeBookings

    private val _pendingBookings = MutableStateFlow<List<Booking>>(emptyList())
    val pendingBookings: StateFlow<List<Booking>> = _pendingBookings

    private val _completedBookings = MutableStateFlow<List<Booking>>(emptyList())
    val completedBookings: StateFlow<List<Booking>> = _completedBookings

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _bookingActionState = MutableStateFlow<BookingActionState?>(null)
    val bookingActionState: StateFlow<BookingActionState?> = _bookingActionState

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                launch { // Separate coroutine for profile picture
                    bookingRepository.getCustomerProfilePicture().collect { picture ->
                        _profilePicture.update { picture }
                        println("Profile Picture Loaded: $picture")
                    }
                }

                launch { // Separate coroutine for bookings
                    bookingRepository.getAllBookings().collect { allBookings ->
                        _bookings.update { allBookings }
                        _activeBookings.update { allBookings.filter { it.status == Booking.BookingStatus.ACTIVE } }
                        _pendingBookings.update { allBookings.filter { it.status == Booking.BookingStatus.PENDING } }
                        _completedBookings.update { allBookings.filter { it.status == Booking.BookingStatus.COMPLETED } }
                        _isLoading.update { false }
                    }
                }
            } catch (e: Exception) {
                _errorMessage.update { "Failed to load data: ${e.localizedMessage}" }
                _isLoading.update { false }
            }
        }
    }



    fun cancelBooking(bookingId: String) {
        viewModelScope.launch {
            try {
                val success = bookingRepository.deleteBooking(bookingId)
                if (success) {
                    _bookings.update { list -> list.filter { it.id != bookingId } }
                    _activeBookings.update { list -> list.filter { it.id != bookingId } }
                    _pendingBookings.update { list -> list.filter { it.id != bookingId } }
                    _completedBookings.update { list -> list.filter { it.id != bookingId } }
                    _bookingActionState.update { BookingActionState.BookingCancelled(bookingId) }
                } else {
                    _errorMessage.update { "Failed to cancel booking." }
                    _bookingActionState.update { BookingActionState.Error("Failed to cancel booking.") }
                }
            } catch (e: Exception) {
                _errorMessage.update { "Error cancelling booking: ${e.localizedMessage}" }
                _bookingActionState.update { BookingActionState.Error("Error cancelling booking: ${e.localizedMessage}") }
            } finally {
                _bookingActionState.update { null } // Reset the action state
            }
        }
    }

    fun resetBookingActionState() {
        _bookingActionState.value = null
    }

    public fun onTabSelected(status: Booking.BookingStatus) {
        _currentTab.update { status }
    }

    private val _currentTab = MutableStateFlow(Booking.BookingStatus.ACTIVE)
    val currentTab: StateFlow<Booking.BookingStatus> = _currentTab

    val displayedBookings: StateFlow<List<Booking>> = _currentTab.flatMapLatest { status ->
        when (status) {
            Booking.BookingStatus.ACTIVE -> _activeBookings
            Booking.BookingStatus.PENDING -> _pendingBookings
            Booking.BookingStatus.COMPLETED -> _completedBookings
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    companion object {
        fun provideFactory(
            bookingRepository: BookingRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
                    return BookingViewModel(bookingRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

sealed class BookingActionState {
    data class StatusUpdated(val bookingId: String) : BookingActionState()
    data class BookingCancelled(val bookingId: String) : BookingActionState()
    data class Error(val message: String) : BookingActionState()
}