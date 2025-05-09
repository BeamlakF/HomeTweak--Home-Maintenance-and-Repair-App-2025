package com.example.myapplication.data

import com.example.myapplication.model.Booking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class FakeBookingRepo : BookingRepo {

    private val _profilePicture = MutableStateFlow<String?>("https://via.placeholder.com/150/FFC107/000000?Text=User")
    private val _bookings = MutableStateFlow<List<Booking>>(
        mutableListOf(
            Booking(
                id = UUID.randomUUID().toString(),
                customerId = "user123",
                providerId = "provider456",
                serviceType = "Haircut",
                serviceProvider = "Salon A",
                price = "30 Birr",
                dateTime = "September 10, 2025. 12:00 AM",
                status = Booking.BookingStatus.ACTIVE
            ),
            Booking(
                id = UUID.randomUUID().toString(),
                customerId = "user789",
                providerId = "provider101",
                serviceType = "Massage",
                serviceProvider = "Spa B",
                price = "60 Birr",
                dateTime = "February 12, 2024. 8:00 AM",
                status = Booking.BookingStatus.PENDING
            ),
            Booking(
                id = UUID.randomUUID().toString(),
                customerId = "user123",
                providerId = "provider456",
                serviceType = "Manicure",
                serviceProvider = "Salon A",
                price = "25",
                dateTime = "March 14, 2023. 3:00 PM",
                status = Booking.BookingStatus.COMPLETED
            ),
            Booking(
                id = UUID.randomUUID().toString(),
                customerId = "user456",
                providerId = "provider789",
                serviceType = "Facial",
                serviceProvider = "Beauty C",
                price = "75 Birr",
                dateTime = "October 10, 2025. 6:00 PM",
                status = Booking.BookingStatus.ACTIVE
            ),
            Booking(
                id = UUID.randomUUID().toString(),
                customerId = "user789",
                providerId = "provider101",
                serviceType = "Pedicure",
                serviceProvider = "Spa B",
                price = "40 Birr",
                dateTime = "June 05, 2024. 8:00 AM",
                status = Booking.BookingStatus.PENDING
            ),
            Booking(
                id = UUID.randomUUID().toString(),
                customerId = "user123",
                providerId = "provider789",
                serviceType = "Hair Color",
                serviceProvider = "Beauty C",
                price = "90 Birr",
                dateTime = "May 10, 2025. 11:00 AM",
                status = Booking.BookingStatus.COMPLETED
            )
        ).toMutableList()
    )

    override fun getCustomerProfilePicture(): Flow<String?> = _profilePicture

    override fun getAllBookings(): Flow<List<Booking>> = _bookings

    override suspend fun deleteBooking(bookingId: String): Boolean {
        delay(500) // Simulate network delay
        val initialSize = _bookings.value.size
        _bookings.update {
            it.filter { booking -> booking.id != bookingId }
        }
        return _bookings.value.size < initialSize
    }
    init {
        _bookings.update { it.toList() }
    }

}
