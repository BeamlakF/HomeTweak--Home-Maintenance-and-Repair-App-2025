
package com.example.hometweak

import androidx.compose.runtime.mutableStateListOf

object BookingRepository {
    private val _activeBookings = mutableStateListOf<Booking>()
    val activeBookings: List<Booking> get() = _activeBookings

    fun addBooking(booking: Booking) {
        _activeBookings.add(booking)
    }
}




data class Booking(
    val id: String = "", 
    val serviceName: String,
    val providerName: String,
    val dateTime: String,
    val customerName: String,
    val price: String,
    val location: String,
    val status: BookingStatus = BookingStatus.ACTIVE
)

enum class BookingStatus {
    ACTIVE, PENDING, COMPLETED
}
