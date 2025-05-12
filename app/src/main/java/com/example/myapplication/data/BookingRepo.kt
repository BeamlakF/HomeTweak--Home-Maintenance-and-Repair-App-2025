package com.example.myapplication.data

import com.example.myapplication.model.Booking
import kotlinx.coroutines.flow.Flow

interface BookingRepo {
    fun getCustomerProfilePicture(): Flow<String?> // Assuming profile picture is a String URL or path
    fun getAllBookings(): Flow<List<Booking>>
    suspend fun deleteBooking(bookingId: String): Boolean
}