package com.example.myapplication.data

import com.example.myapplication.model.Dashboard

interface DashboardRepo {
    suspend fun getServiceProviderProfile(): Dashboard.UserProfile
    suspend fun getBookingSummary(): Dashboard.BookingSummary
    suspend fun getPendingBookings(): List<Dashboard.Booked>
    suspend fun getActiveBookings(): List<Dashboard.Booked>
    suspend fun getCompletedBookings(): List<Dashboard.Booked>
    suspend fun confirmBooking(bookingId: String): Boolean
    suspend fun declineBooking(bookingId: String): Boolean
}