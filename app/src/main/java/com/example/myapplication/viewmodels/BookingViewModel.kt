package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.model.Booking
import com.example.myapplication.data.model.Booking.BookingStatus
import com.example.myapplication.data.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class BookingViewModel(private val bookingRepository: BookingRepository) : ViewModel() {

    private val _profilePicture = MutableStateFlow<Int?>(null)
    val profilePicture: StateFlow<Int?> = _profilePicture

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

    private val _currentTab = MutableStateFlow(BookingStatus.ACTIVE)
    val currentTab: StateFlow<BookingStatus> = _currentTab

    @OptIn(ExperimentalCoroutinesApi::class)
    val displayedBookings: StateFlow<List<Booking>> = currentTab.flatMapLatest { status ->
        when (status) {
            BookingStatus.ACTIVE -> activeBookings
            BookingStatus.PENDING -> pendingBookings
            BookingStatus.COMPLETED -> completedBookings
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            try {
                _profilePicture.value = R.drawable.ic_launcher_foreground // Replace with real image logic

                bookingRepository.getAllBookings().collect { allBookings ->
                    _bookings.value = allBookings
                    _activeBookings.value = allBookings.filter { it.status == BookingStatus.ACTIVE }
                    _pendingBookings.value = allBookings.filter { it.status == BookingStatus.PENDING }
                    _completedBookings.value = allBookings.filter { it.status == BookingStatus.COMPLETED }
                }

            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cancelBooking(bookingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Simulate cancellation
                val updatedList = _bookings.value.filter { it.id != bookingId }
                _bookings.value = updatedList
                _activeBookings.value = updatedList.filter { it.status == BookingStatus.ACTIVE }
                _pendingBookings.value = updatedList.filter { it.status == BookingStatus.PENDING }
                _completedBookings.value = updatedList.filter { it.status == BookingStatus.COMPLETED }

                _bookingActionState.value = BookingActionState.BookingCancelled(bookingId)

            } catch (e: Exception) {
                _errorMessage.value = "Error cancelling booking: ${e.localizedMessage}"
                _bookingActionState.value = BookingActionState.Error("Error cancelling booking")
            } finally {
                _bookingActionState.value = null
            }
        }
    }

    fun onTabSelected(status: BookingStatus) {
        _currentTab.value = status
    }

    fun resetBookingActionState() {
        _bookingActionState.value = null
    }

    companion object {
        fun provideFactory(
            bookingRepository: BookingRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BookingViewModel(bookingRepository) as T
            }
        }
    }
}

sealed class BookingActionState {
    data class BookingCancelled(val bookingId: Int) : BookingActionState()
    data class Error(val message: String) : BookingActionState()
}
